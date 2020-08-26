project="apm"
image="ascap"
nexus_host = 'nexusmaster.ascap.com'
nexus_port = 18444 // port for docker push /* port for docker push */
nexus_flag = true
main_branch = 'master'
pipeline
{
    agent
    {
        label 'ascap_test'
    }
	
    environment
    {
       
        docker_cred= "Docker_hub"
        DockerImage=''
        def pom = readMavenPom(file: 'apm/pom.xml')
        def projectVersion = pom.getVersion()
		def version = projectVersion.trim().substring(0,5)
    }
        stages
            {
             stage('checkout git repo')
                {
                    steps
                    {
                        git credentialsId: 'github-repo', url: 'https://github.com/purushothaman-chainsys/test.git'
                    }
                }
                stage('moving dependency packages')
                {
                    steps
                    {
                        // 'sh mkdir /tmp/java'
                        //sh 'cp -R /var/lib/cloudbees-core-cm/workspace/apm_online_cloud/apm/jdk-11 /tmp/'
                        //sh 'ls -ltr /tmp/jdk-11/bin/'
                        sh 'docker ps'
                        sh 'hostname -i'
                        sh 'hostname'
                        sh 'ls -ltr /var/lib/cloudbees-core-cm/workspace/apm_online_cloud/apm'
                        //sh 'echo ${projectVersion}'
                        sh './mvnw install:install-file -Dfile=${WORKSPACE}/apm_config/ojdbc6-11.2.0.3.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0.3.0 -Dpackaging=jar'
                        sh './mvnw install:install-file -Dfile=${WORKSPACE}/apm_config/sqljdbc4.jar -DgroupId=sqlserver -DartifactId=sqljdbc4 -Dversion=4.0 -Dpackaging=jar'
                        sh './mvnw install:install-file -Dfile=${WORKSPACE}/apm_config/bcm.jar -DgroupId=bcm -DartifactId=bcm -Dversion=1.0 -Dpackaging=jar'
                        sh './mvnw install:install-file -Dfile=${WORKSPACE}/apm_config/cecore.jar -DgroupId=cecore -DartifactId=cecore -Dversion=1.3 -Dpackaging=jar'
                        sh './mvnw install:install-file -Dfile=${WORKSPACE}/apm_config/celib.jar -DgroupId=celib -DartifactId=celib -Dversion=1.0 -Dpackaging=jar'
                        sh './mvnw install:install-file -Dfile=${WORKSPACE}/apm_config/cesession.jar -DgroupId=cesession -DartifactId=cesession -Dversion=1.0 -Dpackaging=jar'
                        sh './mvnw install:install-file -Dfile=${WORKSPACE}/apm_config/logging.jar -DgroupId=logging -DartifactId=logging -Dversion=1.0 -Dpackaging=jar'
                        sh './mvnw install:install-file -Dfile=${WORKSPACE}/apm_config/ceaspect.jar -DgroupId=ceaspect -DartifactId=ceaspect -Dversion=1.0 -Dpackaging=jar'
                        sh './mvnw install:install-file -Dfile=${WORKSPACE}/apm_config/TraceLog.jar -DgroupId=TraceLog -DartifactId=TraceLog -Dversion=1.0 -Dpackaging=jar'
                        sh './mvnw install:install-file -Dfile=${WORKSPACE}/apm_config/corbaidl.jar -DgroupId=corbaidl -DartifactId=corbaidl -Dversion=1.0 -Dpackaging=jar'
                        sh './mvnw install:install-file -Dfile=${WORKSPACE}/apm_config/ebus405.jar -DgroupId=ebus405 -DartifactId=ebus405 -Dversion=11.5.8 -Dpackaging=jar'
                        sh './mvnw install:install-file -Dfile=${WORKSPACE}/apm_config/cryptojFIPS.jar -DgroupId=cryptojFIPS -DartifactId=cryptojFIPS -Dversion=4.1 -Dpackaging=jar'
                        sh './mvnw install:install-file -Dfile=${WORKSPACE}/apm_config/cereports.jar -DgroupId=cereports -DartifactId=cereports -Dversion=14.0.9 -Dpackaging=jar'
                        sh './mvnw install:install-file -Dfile=${WORKSPACE}/apm_config/CrystalReportsSDK.jar -DgroupId=CrystalReportsSDK -DartifactId=CrystalReportsSDK -Dversion=14.0.9 -Dpackaging=jar'
                        sh './mvnw install:install-file -Dfile=${WORKSPACE}/apm_config/SL_plugins.jar -DgroupId=SL_plugins -DartifactId=SL_plugins -Dversion=1.0 -Dpackaging=jar'
                        sh './mvnw install:install-file -Dfile=${WORKSPACE}/apm_config/webreporting.jar -DgroupId=webreporting -DartifactId=webreporting -Dversion=11.5.8 -Dpackaging=jar'
                        sh './mvnw install:install-file -Dfile=${WORKSPACE}/apm_config/httpclient-4.5.8.jar -DgroupId=org.apache.httpcomponents -DartifactId=httpclient -Dversion=4.5.8 -Dpackaging=jar'
                        sh './mvnw install:install-file -Dfile=${WORKSPACE}/apm_config/httpcore-nio-4.4.13.jar -DgroupId=org.apache.components -DartifactId=httpcore-nio -Dversion=4.4.13 -Dpackaging=jar'
                        sh './mvnw install:install-file -Dfile=${WORKSPACE}/apm_config/httpmime-4.5.11.jar -DgroupId=org.apache.components -DartifactId=httpmime -Dversion=4.5.11 -Dpackaging=jar'
                        sh './mvnw -v'
                        sh 'java -version'
                        sh 'docker images'
                    }
                }
                stage('Build')
                {
                    steps
                    {
                        sh './mvnw -f ${WORKSPACE}/apm/pom.xml clean install '
                      
                    }
                }
                /*stage('Build Docker Image')
                {
                    steps
                    {
                        script
                        {
							//def version = sh (script: "./mvnw help:evaluate -Dexpression=project.version | grep -e '^[^\\[]'", returnStdout: true).trim().substring(0,5)
							def commit = "${env.GIT_COMMIT}".substring(0,7)
							print("${version}-${commit}")                      
							sh "docker build -f Dockerfile -t ${project}/${image}:${version} ."
							
                          
                        }
                    }
                }
                
                stage('Push Docker Image to Nexus')
                {
					when
					{
						expression {nexus_flag}
						expression { shouldPublishToNexus(project, image, nexus_host) }
					}
                    steps
                    {
                        script
                        {
							//def version = sh (script: "./mvnw help:evaluate -Dexpression=project.version | grep -e '^[^\\[]'", returnStdout: true).trim().substring(0,5)
							def commit = "${env.GIT_COMMIT}".substring(0,7)
							print("${version}-${commit}") 
							createNexusTag(project, image, version, commit, nexus_host)
                            withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId:'NexusAdmin', usernameVariable: 'nexus_user', passwordVariable: 'nexus_pswd']])
							{
                                sh "docker tag $project/$image:$version $nexus_host:$nexus_port/$project/$image"
								sh "docker tag $project/$image:$version $nexus_host:$nexus_port/$project/$image:$version"

								sh "docker push $nexus_host:$nexus_port/$project/$image:$version"
								sh "docker push $nexus_host:$nexus_port/$project/$image"
                            }
							 associateNexusTag(project, image, version, nexus_host)
                        }
                    }
                }
        }
        post
        {
            always
            {
                docker_clean()
            }
        }
        
}

def createNexusTag(String app_name, String target, String version, String commit, String nexus_host)
{
    def nexus_url = "https://${nexus_host}/service/rest/v1/tags"
    def tag_name  = "${app_name}-${target}-${version}"
    def payload   = [name: "${tag_name}", attributes: [name:"${app_name}",commit:"${commit}", version:"${version}", target:"${target}"]]
    def toJson    = {input -> groovy.json.JsonOutput.toJson(input)}
    def response  = httpRequest httpMode: 'POST', url: nexus_url, authentication: 'NexusAdmin', requestBody: toJson(payload), validResponseCodes: '200', acceptType: 'APPLICATION_JSON', contentType: 'APPLICATION_JSON'
}

def readNexusTag(String app_name, String target, String version, String nexus_host)
{
    def tag_name  = "${app_name}-${target}-${version}"
    def nexus_url = "https://${nexus_host}/service/rest/v1/tags/${tag_name}"
    def response  = httpRequest httpMode: 'GET', url: nexus_url, authentication: 'NexusAdmin', validResponseCodes: '200,404', acceptType: 'APPLICATION_JSON'
    if (response.status == 200)
    {
        def data = readJSON text: response.content
        return data['attributes']
    }
}

def associateNexusTag(String app_name, String target, String version, String nexus_host)
{
    def tag_name  = "${app_name}-${target}-${version}"
    def nexus_url = "https://${nexus_host}/service/rest/v1/tags/associate/${tag_name}?wait=true&repository=docker-internal&format=docker&name=${app_name}/${target}&version=${version}"
    def response  = httpRequest httpMode: 'POST', url: nexus_url, authentication: 'NexusAdmin', validResponseCodes: '200,404', acceptType: 'APPLICATION_JSON', contentType: 'APPLICATION_JSON'
    if (response.status == 404)
        error "could not associate tag with artifact"
}

def shouldPublishToNexus(String app_name, String target, String nexus_host)
{
	def pom = readMavenPom(file: 'apm/pom.xml')
        def projectVersion = pom.getVersion()
	def version = projectVersion.trim().substring(0,5)
	def commit = "${env.GIT_COMMIT}".substring(0,7)
    def nexus_tag = readNexusTag(app_name, target, version, nexus_host)
    print "nexus_tag: ${nexus_tag}"
    if (nexus_tag == null)
        return true
    if (nexus_tag['commit'] == commit)
        return false
    else
        error "artifact version already exists with a different commit"
}
def docker_clean()
{
    def project = 'apm'
    def image = 'ascap'
    sh "chmod +x cicd/docker-clean.sh"
    sh "./cicd/docker-clean.sh ${project}/${image}"
}*/
	    }
	
}
	
	
