'use strict';

angular.module('portScanApp').factory('PortScanService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
            		loadAllPorts: loadAllPorts,
            		getAllPorts: getAllPorts,
            };

            return factory;

            function loadAllPorts(host) {
                console.log('Fetching all ports');
                var deferred = $q.defer();
                $http.get(urls.PORT_SCAN_SERVICE_API+'?input='+host + '&isLatestScanRequired=true')
                    .then(
                        function (response) {
                            console.log('Fetched successfully all ports');
                            $localStorage.ports = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading ports');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllPorts(host){
            	loadAllPorts(host);
                return $localStorage.ports;
            }
        }
    ]);