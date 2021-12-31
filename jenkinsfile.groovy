pipeline {
    agent any

    environment {
        master="https://github.com/ramesh2617/master.git"
        slave="https://github.com/ramesh2617/slave.git"
    }    

	parameters {
	    string(name: 'master', description:'testing')
	    string(name: 'slave', description:'testing')
	}
	
    stages  {	
        stage('Set Git Config'){
		  steps {
            sh 'git config --global user.email "krameshchennai3456@gmail.com"'
            sh 'git config --global user.name "ramesh"'
            sh 'git config --global credential.helper cache'
            sh "git config --global credential.helper 'cache --timeout=4500'"
          }
		}  
        stage('Set Git Credentials'){
		  steps{
            git credentialsId: 'b6fc8a44-5b4f-4c05-b741-d7da73334889', url: 'https://github.com/ramesh2617/master.git'
            git credentialsId: 'b6fc8a44-5b4f-4c05-b741-d7da73334889', url: 'https://github.com/ramesh2617/slave.git'
          }
		}  

        stage('Syncronize master'){
		  steps {
            sh script.sh  
            sh 'git clone --mirror ${https://github.com/ramesh2617/master.git}feature.git'
            dir("master.git"){
               //add a remote repository
               sh ' git push --mirror ${https://github.com/ramesh2617/slave.git}'
               // update the local copy from the first repository
               sh 'git fetch origin --tags'

               // update the local copy with the second repository
               sh 'git fetch slave --tags'

               // sync back the second repository
               sh 'git push slave --all'
               sh 'git push slave --tags'
			}	
		  }  
        }
    }
}
