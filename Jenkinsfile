project="testonchainsys"
image="apmtest"
nexus_host=" http://3.129.16.232:8081"
nexus_url=" http://3.129.16.232"
nexus_port= 8085 /* port for docker push */
nexus_flag = true
main_branch = 'master'
pipeline
{
    agent any;
    environment
    {
        mvn ="/opt/apache-maven-3.6.3/bin/mvn"
        docker_registry = "testonchainsys"
        
        docker_cred= "Docker_hub"
        DockerImage=''
    }
        stages
            {
                /*stage('checkout git repo')
                {
                    steps
                    {
                        //cleanWs()
                        git url: 'https://github.com/purushothaman-chainsys/test.git'
                    }
                }*/
                stage('moving dependency packages')
                {
                    steps
                    {
                        sh '${mvn} install:install-file -Dfile=${WORKSPACE}/CS_Devops/apm_config/ojdbc6-11.2.0.3.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0.3.0 -Dpackaging=jar'
                        sh '${mvn} install:install-file -Dfile=${WORKSPACE}/CS_Devops/apm_config/sqljdbc4.jar -DgroupId=com.microsoft.sqlserver -DartifactId=sqljdbc4 -Dversion=4.0 -Dpackaging=jar'
                        sh '${mvn} install:install-file -Dfile=${WORKSPACE}/CS_Devops/apm_config/bcm.jar -DgroupId=bcm -DartifactId=bcm -Dversion=1.0 -Dpackaging=jar'
                        sh '${mvn} install:install-file -Dfile=${WORKSPACE}/CS_Devops/apm_config/cecore.jar -DgroupId=cecore -DartifactId=cecore -Dversion=1.3 -Dpackaging=jar'
                        sh '${mvn} install:install-file -Dfile=${WORKSPACE}/CS_Devops/apm_config/celib.jar -DgroupId=celib -DartifactId=celib -Dversion=1.0 -Dpackaging=jar'
                        sh '${mvn} install:install-file -Dfile=${WORKSPACE}/CS_Devops/apm_config/cesession.jar -DgroupId=cesession -DartifactId=cesession -Dversion=1.0 -Dpackaging=jar'
                        sh '${mvn} install:install-file -Dfile=${WORKSPACE}/CS_Devops/apm_config/logging.jar -DgroupId=logging -DartifactId=logging -Dversion=1.0 -Dpackaging=jar'
                        sh '${mvn} install:install-file -Dfile=${WORKSPACE}/CS_Devops/apm_config/ceaspect.jar -DgroupId=ceaspect -DartifactId=ceaspect -Dversion=1.0 -Dpackaging=jar'
                        sh '${mvn} install:install-file -Dfile=${WORKSPACE}/CS_Devops/apm_config/TraceLog.jar -DgroupId=TraceLog -DartifactId=TraceLog -Dversion=1.0 -Dpackaging=jar'
                        sh '${mvn} install:install-file -Dfile=${WORKSPACE}/CS_Devops/apm_config/corbaidl.jar -DgroupId=corbaidl -DartifactId=corbaidl -Dversion=1.0 -Dpackaging=jar'
                        sh '${mvn} install:install-file -Dfile=${WORKSPACE}/CS_Devops/apm_config/ebus405.jar -DgroupId=ebus405 -DartifactId=ebus405 -Dversion=11.5.8 -Dpackaging=jar'
                        sh '${mvn} install:install-file -Dfile=${WORKSPACE}/CS_Devops/apm_config/cryptojFIPS.jar -DgroupId=cryptojFIPS -DartifactId=cryptojFIPS -Dversion=4.1 -Dpackaging=jar'
                        sh '${mvn} install:install-file -Dfile=${WORKSPACE}/CS_Devops/apm_config/cereports.jar -DgroupId=cereports -DartifactId=cereports -Dversion=14.0.9 -Dpackaging=jar'
                        sh '${mvn} install:install-file -Dfile=${WORKSPACE}/CS_Devops/apm_config/CrystalReportsSDK.jar -DgroupId=CrystalReportsSDK -DartifactId=CrystalReportsSDK -Dversion=14.0.9 -Dpackaging=jar'
                        sh '${mvn} install:install-file -Dfile=${WORKSPACE}/CS_Devops/apm_config/SL_plugins.jar -DgroupId=SL_plugins -DartifactId=SL_plugins -Dversion=1.0 -Dpackaging=jar'
                        sh '${mvn} install:install-file -Dfile=${WORKSPACE}/CS_Devops/apm_config/webreporting.jar -DgroupId=webreporting -DartifactId=webreporting -Dversion=11.5.8 -Dpackaging=jar'
                        sh '${mvn} install:install-file -Dfile=${WORKSPACE}/CS_Devops/apm_config/httpclient-4.5.8.jar -DgroupId=org.apache.httpcomponents -DartifactId=httpclient -Dversion=4.5.8 -Dpackaging=jar'
                    }
                }
                stage('Build')
                {
                    steps
                    {
                        sh '${mvn} -f ${WORKSPACE}/CS_Devops/apm/ clean install -DskipTests=true'
                      
                    }
                }
                stage('Build Docker Image')
                {
                    steps
                    {
                        script
                        {
                           //DockerImage = docker.build docker_registry + ":$BUILD_NUMBER"
                           
                          sh 'docker build -f Dockerfile -t $docker_registry:$BUILD_NUMBER .'
                          //  sh 'sample'
                          
                        }
                    }
                }
                
                stage('Push Docker Image to Nexus')
                {
                    when 
                    {
                         expression {nexus_flag}
                        // expression { shouldPublishToNexus(project, image, nexus_host) }
                    }
                    steps
                    {
                        script
                        {   
                            def version = sh (script: "${mvn} -v", returnStdout: true).trim().substring(14,18)
                            def commit  = "${env.GIT_COMMIT}".substring(0,7)
                            print("print values are => ${version}-${commit}")
                            createNexusTag(project, image, version, commit, nexus_host)
                            withCredentials([usernamePassword(credentialsId: 'NexusAdmin', passwordVariable: 'nexus_pswd', usernameVariable: 'nexus_user')])
                            {
                                sh "docker login -u $nexus_user -p $nexus_pswd $nexus_url:$nexus_port"
                                sh "docker tag $docker_registry:$BUILD_NUMBER $nexus_url:$nexus_port/$docker_registry:$BUILD_NUMBER"
                                //sh "docker push $nexus_url:$nexus_port/$docker_registry:$BUILD_NUMBER"
                            }
                        }
                    }
                }
        }
        
}
def shouldPublishToNexus(String app_name, String target, String nexus_host)
{
    def version = sh (script: "${mvn} -v", returnStdout: true).trim().substring(14,18)
    def commit    = "${env.GIT_COMMIT}".substring(0,7)
    def nexus_tag = readNexusTag(app_name, target, version, nexus_host)
    print "nexus_tag: ${nexus_tag}"
    print "commit value is ": ${commit}
    if (nexus_tag == null)
        return true
    if (nexus_tag['commit'] == commit)
        return false
    else
        error "artifact version already exists with a different commit"
}

def readNexusTag(String app_name, String target, String version, String nexus_host)
{
    def tag_name  = "${app_name}-${target}-${version}"
    def nexus_url = "http://${nexus_host}/service/rest/v1/tags/${tag_name}"
    def response  = httpRequest httpMode: 'GET', url: nexus_url, authentication: 'NexusAdmin', validResponseCodes: '200,404', acceptType: 'APPLICATION_JSON'
    if (response.status == 200)
    {
        def data = readJSON text: response.content
        return data['attributes']
    }
}
 
