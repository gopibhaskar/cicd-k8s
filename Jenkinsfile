#!groovy
def buildNumber
def workspace
def props=''
def tagName="""${JOB_NAME}-${BUILD_TIMESTAMP}"""
def commit_username
def commit_Email
def imageName

node {
    stage('Checkout Code') {
        props = readProperties  file: """deploy.properties"""
        checkout scm

        sh 'git log --format="%ae" | head -1 > commit-author.txt'
        commit_Email = readFile('commit-author.txt').trim()
        sh 'git show -s --pretty=%an > committer.txt'
        commit_username = readFile('committer.txt').trim()
        buildNumber = """${BUILD_NUMBER}"""
        echo buildNumber
        echo commit_username
        echo commit_Email
    }
    stage("Build and Compile Stage"){
        try {
	    sh "chmod +x gradlew"
	    sh "source ./hash_code.sh"
            sh "./gradlew clean compileTestJava"
        } catch (err) {
            currentBuild.result='FAILURE'
            notifyBuild(currentBuild.result, "At Stage Compile ", "", commit_Email)
            throw err
        }
    }
    stage("Assemble Stage"){
        try {
            sh "./gradlew clean war"
	    sh "./gradlew assemble"
        } catch (err) {
            currentBuild.result='FAILURE'
            notifyBuild(currentBuild.result, "At Stage Assemble ", "", commit_Email)
            throw err
        }
    }

    stage("Test Stage"){
        try {
            sh "./gradlew test"
        } catch (err) {
            currentBuild.result='FAILURE'
            notifyBuild(currentBuild.result, "At Stage Test", "", commit_Email)
            throw err
        }
    }
    stage("Sonar Security code scan") {
        try {
                withSonarQubeEnv("SonarQContainer") { // Will pick the global server connection you have configured
                    sh './gradlew sonarqube'
                }
        }catch (err) {
            currentBuild.result='FAILURE'
            notifyBuild(currentBuild.result, "At Stage Test", "", commit_Email)
            throw err
        }
    }
    stage("Copy war to workspace"){
        workspace= pwd()
	    
	    sh 'cp -R ./build/libs/*war .'
    }
    stage("Deploy a war to Docker image and Push"){
        try {
            imageName="""${props['registry']}/${props['appname']}:${buildNumber}"""
            sh """docker build -t '${imageName}' ."""
        } catch (err) {
            currentBuild.result='FAILURE'
            notifyBuild(currentBuild.result, "At Stage Docker build", "", commit_Email)
            throw err
        }
    }
    stage("Deploy Environment") {
        try {
            sh "ansible-playbook -i localhost deployment.yml -e versionNum=${buildNumber}"
        }catch (err) {
            currentBuild.result='FAILURE'
            notifyBuild(currentBuild.result, "At Stage Test", "", commit_Email)
            throw err
        }
    }

    stage('Selenium NG Test') {
            try {
                sh "./gradlew test"
            } catch (err) {
                throw  err

            } finally {
                publishHTML(target: [
                        reportDir  : 'target/site/serenity',
                        reportFiles: 'index.html',
                        reportName : "test-result.xml"
                ])
            }
        }
        stage('Results') {
            junit '**/target/failsafe-reports/*.xml'
        }
    }
def notifyBuild(String buildStatus, String buildFailedAt, String bodyDetails, String commit_Email) 
{
	buildStatus = buildStatus ?: 'SUCCESS'
	def details = """Please find attahcment for log and Check console output at ${BUILD_URL}\n \n "${bodyDetails}"
	\n"""
	emailext attachLog: true,
	recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
	body: details, 
	subject: """${buildStatus}: Job ${JOB_NAME} [${BUILD_NUMBER}] ${buildFailedAt}""", 
	to: """${commit_Email}"""
}
