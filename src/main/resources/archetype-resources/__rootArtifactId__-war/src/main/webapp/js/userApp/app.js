var userApp = angular.module('userApp', [
    'ngRoute',
    'userAppControllers'
]);

userApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/imprint', {
                templateUrl: '../imprint.html'
            }).
            when('/about', {
                templateUrl: '../about.html'
            }).
            otherwise({
                templateUrl: '../welcome.html'
            });
    }]);