moment.locale('pt_BR');

var app = angular.module('myApp', []);
app.controller('myCtrl', function ($scope, $http) {

	$scope.getParameterByName = function (name, url) {
		if (!url) url = window.location.href;
		name = name.replace(/[\[\]]/g, "\\$&");
		var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"), results = regex.exec(url);
		if (!results) return null;
		if (!results[2]) return '';
		return decodeURIComponent(results[2].replace(/\+/g, " "));
	};

	$scope.city = $scope.getParameterByName('city');
	$scope.moment = moment;
	$http.get('rest/previsão/' + $scope.city)
		.then(function (response) {
			$scope.resp = response.data;
		});
});