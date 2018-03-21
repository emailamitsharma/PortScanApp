var app = angular.module('portScanApp',['ui.router','ngStorage']);

app.constant('urls', {//Better mechanism can be thought for property management for UI.
    BASE: 'http://akshima.in:8082/PortScanAPI',
    //BASE: 'http://localhost:8080/PortScanAPI',
    PORT_SCAN_SERVICE_API : 'http://akshima.in:8082/PortScanAPI/api/ports'
    //PORT_SCAN_SERVICE_API : 'http://localhost:8080/PortScanAPI/api/ports'
});

app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'partials/list',
                controller:'PortScanController',
                controllerAs:'ctrl'               
            });
        $urlRouterProvider.otherwise('/');
    }]);


