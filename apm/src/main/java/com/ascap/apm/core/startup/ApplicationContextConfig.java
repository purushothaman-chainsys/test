package com.ascap.apm.core.startup;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.theme.SessionThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersByPathRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersByPathResult;
import com.amazonaws.services.simplesystemsmanagement.model.Parameter;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.constants.PrepPropertiesConstants;
import com.ascap.apm.core.utils.EncryptionUtils;
import com.ascap.apm.core.utils.ParameterStorePropertySource;

import redis.clients.jedis.JedisPoolConfig;

/**
 * The Class ApplicationContextConfig.
 */
@EnableWebMvc
@Configuration
@EnableTransactionManagement
@EnableRedisHttpSession(redisNamespace = "ascapapm:session")
@ComponentScan(basePackages = {"com.ascap.apm"})
public class ApplicationContextConfig implements WebMvcConfigurer {

    /** The ajp port. */
    @Value("${ajp.port}")
    private int ajpPort;

    /** The ajp enabled. */
    @Value("${ajp.enabled}")
    private String ajpEnabled;

    /** The server port. */
    @Value("${server.port}")
    private int serverPort;

    /**
     * Servlet container.
     *
     * @return the tomcat servlet web server factory
     */
    @Bean
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {

            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        if (Boolean.parseBoolean(ajpEnabled))
            tomcat.addAdditionalTomcatConnectors(createAjpConnector());
        return tomcat;
    }

    /**
     * Creates the ajp connector.
     *
     * @return the connector
     */
    private Connector createAjpConnector() {
        Connector connector = new Connector("org.apache.coyote.ajp.AjpNio2Protocol");
        connector.setScheme("AJP/1.3");
        connector.setRedirectPort(serverPort);
        connector.setSecure(false);
        connector.setPort(ajpPort);
        return connector;
    }

    /**
     * Adds the resource handlers.
     *
     * @param registry the registry
     */
    /*
     * (non-Javadoc)
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#
     * addResourceHandlers(org.springframework.web.servlet.config.annotation. ResourceHandlerRegistry)
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:resources");
        registry.addResourceHandler("/themes/**").addResourceLocations("/themes/").setCachePeriod(31556926);
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/images/").setCachePeriod(31556926);
    }

    /**
     * View resolver.
     *
     * @return the internal resource view resolver
     */

    @Bean(name = "viewResolver")
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setOrder(2);
        viewResolver.setPrefix("/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    /**
     * Message source.
     *
     * @return the message source
     */

    @Bean("messageSource")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:locale/admin/AdminResources", "classpath:locale/common/CommonResources",
            "classpath:locale/preferences/PreferencesResources", "classpath:locale/usage/UsageResources",
            "classpath:locale/ApplicationResources", "classpath:ErrorMessages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    @Bean
    public RequestInterceptor pageRequestInterceptor() {
        return new RequestInterceptor();
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("en", "US"));
        return localeResolver;
    }

    /**
     * Theme source.
     *
     * @return the resource bundle theme source
     */
    @Bean
    public ResourceBundleThemeSource themeSource() {
        ResourceBundleThemeSource themeSource = new ResourceBundleThemeSource();
        themeSource.setDefaultEncoding("UTF-8");
        themeSource.setBasenamePrefix("themes.");
        return themeSource;
    }

    /**
     * Theme resolver.
     *
     * @return the session theme resolver
     */
    @Bean
    public SessionThemeResolver themeResolver() {
        SessionThemeResolver resolver = new SessionThemeResolver();
        resolver.setDefaultThemeName("sunny");
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("locale");
        registry.addInterceptor(localeInterceptor);

        ThemeChangeInterceptor themeInterceptor = new ThemeChangeInterceptor();
        themeInterceptor.setParamName("theme");
        registry.addInterceptor(themeInterceptor);

        registry.addInterceptor(pageRequestInterceptor());
    }

    @Bean("propertySource")
    public ParameterStorePropertySource getParameterStorePropertySource() {
        return propertyConfigure();
    }

    @Bean("propertyStoreList")
    public List<Parameter> getPropertyStoreList() {
        GetParametersByPathResult result = null;
        List<Parameter> parameterList = new ArrayList<>();
        String nextToken = null;

        AWSCredentialsProvider credentials = DefaultAWSCredentialsProviderChain.getInstance();
        AWSSimpleSystemsManagement client =
            AWSSimpleSystemsManagementClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain())
                .withCredentials(credentials).withRegion("us-east-1").build();
        do {
            result = client.getParametersByPath(new GetParametersByPathRequest()
                .withPath(PrepPropertiesConstants.AWS_PROPERTIES_STORE_PREFIX).withNextToken(nextToken));
            nextToken = result.getNextToken();
            parameterList.addAll(result.getParameters());
        } while (nextToken != null);

        return parameterList;
    }

    /**
     * Configure default servlet handling.
     *
     * @param configurer the configurer
     */
    /*
     * (non-Javadoc)
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#
     * configureDefaultServletHandling(org.springframework.web.servlet.config.
     * annotation.DefaultServletHandlerConfigurer)
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean("ascapDS")
    public DataSource ascapDataSource() {
        ParameterStorePropertySource propertySource = getParameterStorePropertySource();
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName((String) propertySource.getProperty(
            PrepPropertiesConstants.AWS_PROPERTIES_STORE_PREFIX + PrepPropertiesConstants.ORACLE_JDBC_CLASSNAME));
        bds.setUrl((String) propertySource.getProperty(
            PrepPropertiesConstants.AWS_PROPERTIES_STORE_PREFIX + PrepPropertiesConstants.ORACLE_JDBC_URL));
        bds.setUsername((String) propertySource.getProperty(
            PrepPropertiesConstants.AWS_PROPERTIES_STORE_PREFIX + PrepPropertiesConstants.ORACLE_JDBC_USERNAME));
        try {
            bds.setPassword(EncryptionUtils.decrypt((String) propertySource.getProperty(
                PrepPropertiesConstants.AWS_PROPERTIES_STORE_PREFIX + PrepPropertiesConstants.ORACLE_JDBC_PASSWORD)));
        } catch (PrepSystemException e) {
            Logger.getLogger(ApplicationContextConfig.class).error(e);
        }
        bds.setMinIdle(Integer.parseInt(propertySource
            .getProperty(
                PrepPropertiesConstants.AWS_PROPERTIES_STORE_PREFIX + PrepPropertiesConstants.ORACLE_JDBC_MINPOOL)
            .toString()));
        bds.setMaxIdle(Integer.parseInt(propertySource
            .getProperty(
                PrepPropertiesConstants.AWS_PROPERTIES_STORE_PREFIX + PrepPropertiesConstants.ORACLE_JDBC_MAXPOOL)
            .toString()));
        return bds;
    }

    @Bean("ascapTxManager")
    public PlatformTransactionManager ascapTransactionManager() {
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(ascapDataSource());
        return txManager;
    }

    @Bean("ascapJdbcTemplate")
    public JdbcTemplate ascapJdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(ascapDataSource());
        return jdbcTemplate;
    }

    @Bean("eoDS")
    public DataSource eoDSDataSource() {
        ParameterStorePropertySource propertySource = getParameterStorePropertySource();
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName((String) propertySource.getProperty(
            PrepPropertiesConstants.AWS_PROPERTIES_STORE_PREFIX + PrepPropertiesConstants.MSSQL_JDBC_CLASSNAME));
        bds.setUrl((String) propertySource
            .getProperty(PrepPropertiesConstants.AWS_PROPERTIES_STORE_PREFIX + PrepPropertiesConstants.MSSQL_JDBC_URL));
        bds.setUsername((String) propertySource.getProperty(
            PrepPropertiesConstants.AWS_PROPERTIES_STORE_PREFIX + PrepPropertiesConstants.MSSQL_JDBC_USERNAME));
        try {
            bds.setPassword(EncryptionUtils.decrypt((String) propertySource.getProperty(
                PrepPropertiesConstants.AWS_PROPERTIES_STORE_PREFIX + PrepPropertiesConstants.MSSQL_JDBC_PASSWORD)));
        } catch (PrepSystemException e) {
            Logger.getLogger(ApplicationContextConfig.class).error(e);
        }
        bds.setMinIdle(Integer.parseInt(propertySource
            .getProperty(
                PrepPropertiesConstants.AWS_PROPERTIES_STORE_PREFIX + PrepPropertiesConstants.MSSQL_JDBC_MINPOOL)
            .toString()));
        bds.setMaxIdle(Integer.parseInt(propertySource
            .getProperty(
                PrepPropertiesConstants.AWS_PROPERTIES_STORE_PREFIX + PrepPropertiesConstants.MSSQL_JDBC_MAXPOOL)
            .toString()));
        return bds;
    }

    @Bean("eoDSTxManager")
    public PlatformTransactionManager eoDSTransactionManager() {
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(eoDSDataSource());
        return txManager;
    }

    @Bean("eoDSJdbcTemplate")
    public JdbcTemplate eoDSJdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(eoDSDataSource());
        return jdbcTemplate;
    }

    @Bean
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public ServletRegistrationBean<StartupServlet> getServletRegistrationBean() {
        ServletRegistrationBean<StartupServlet> bean = new ServletRegistrationBean<>(new StartupServlet(), "/startup");
        bean.setLoadOnStartup(1);
        return bean;
    }

    @Bean
    public JedisConnectionFactory connectionFactory() {
        ParameterStorePropertySource propertySource = getParameterStorePropertySource();
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(5);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);

        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.clusterNode(
            (String) propertySource.getProperty(
                PrepPropertiesConstants.AWS_PROPERTIES_STORE_PREFIX + PrepPropertiesConstants.AWS_REDIS_CLUSTER_HOST),
            Integer.parseInt((String) propertySource.getProperty(
                PrepPropertiesConstants.AWS_PROPERTIES_STORE_PREFIX + PrepPropertiesConstants.AWS_REDIS_CLUSTER_PORT)));
        redisClusterConfiguration.setPassword((String) propertySource.getProperty(
            PrepPropertiesConstants.AWS_PROPERTIES_STORE_PREFIX + PrepPropertiesConstants.AWS_REDIS_CLUSTER_PASSWORD));
        JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofSeconds(60));
        jedisClientConfiguration.usePooling().poolConfig(poolConfig);
        jedisClientConfiguration.useSsl();
        return new JedisConnectionFactory(redisClusterConfiguration, jedisClientConfiguration.build());
    }

    @Bean
    public ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    public ParameterStorePropertySource propertyConfigure() {
        AWSCredentialsProvider credentials = DefaultAWSCredentialsProviderChain.getInstance();
        AWSSimpleSystemsManagement simpleSystemsManagementClient = AWSSimpleSystemsManagementClientBuilder.standard()
            .withCredentials(credentials).withRegion("us-east-1").build();
        ParameterStorePropertySource propertySource = new ParameterStorePropertySource(
            PrepPropertiesConstants.AWS_PROPERTIES_STORE_PREFIX.replace("/", ""), simpleSystemsManagementClient);
        return propertySource;
    }
}
