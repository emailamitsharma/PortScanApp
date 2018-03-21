'use strict';

angular.module('portScanApp').controller('PortScanController',
		['PortScanService', '$scope',  function( PortScanService, $scope) {

			var ui = this;
			var blank = "";
			ui.host = blank;
			ui.ports=[];

			ui.submit = submit;
			ui.getAllPorts = getAllPorts;


			ui.successMessage = '';
			ui.errorMessage = '';
			ui.done = false;

			ui.onlyIntegers = /^\d+$/;
			ui.onlyNumbers = /^\d+([,.]\d+)?$/;

			function submit() {
				console.log('Submitting with input value ' + ui.host);
				
				if (ui.host === undefined || ui.host === null) {
					ui.successMessage=blank;
					ui.errorMessage="Please enter required input of I.P Address/Host Name."
						return;
				}
				if(!ValidateInput(ipRegEx(),hostRegEx(),ui.host)){
					ui.successMessage=blank;
					ui.errorMessage="Please enter correct I.P Address/Host Name"
						return;
				}	
				ui.ports = getAllPorts(ui.host);
				
				$scope.myForm.$setPristine();
			}

			function getAllPorts(host){
				return PortScanService.loadAllPorts(host).then(
	                    function (response) {
	                    	if(response !=null && response.data.message=='No data available for input request'){
	                    		
	                    		console.error('Error while pulling ports information' + response.data.message);
		                        ui.errorMessage = 'No data available for input request :( ';
		                        ui.successMessage=blank;
	                    	
	                    	}else{
	                    		console.log('Ports retrieved successfully');
		                        ui.successMessage = 'Ports retrieved successfully';
		                        ui.ports=response.data;
		                        ui.errorMessage=blank;
		                        ui.done = true;
	                    	}
	                        
	                    },
	                    function (errResponse) {
	                        console.error('Error while pulling ports information');
	                        ui.errorMessage = 'There is some error while serving this request. Please bear with us for some time to analyze what went wrong :( ';
	                        ui.successMessage=blank;
	                    }
	                );
			}

			function ipRegEx(){        	
				 var ipRE = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
				return ipRE;
			}


			function hostRegEx(){        	
 				var hostRE = new RegExp(/^((?:(?:(?:\w[\.\-\+]?)*)\w)+)((?:(?:(?:\w[\.\-\+]?){0,62})\w)+)\.(\w{2,6})$/); 
				return hostRE;
			}


			function ValidateInput(ipRE,hostRE, inputValue) {         	
				return ipRE.test(inputValue) || hostRE.test(inputValue);
			} 

		}


		]);