(function () {
	//Sets time i18n to portugues
	moment.locale('pt_BR');

	var app = angular.module('myApp', []);
	app.controller('myCtrl', function ($scope, $http) {

		$scope.errorDisplay = false;

		$scope.city = getParameterByName('city');
		$scope.moment = moment;
		$http.get('rest/previsão/' + $scope.city)
			.then(function (response) {
				$scope.resp = response.data;
			}, function (response) {
				if (response.status == 406) {
					$scope.errorString = "Nome de cidade '" + $scope.city + "'não existe!";
				} else {
					$scope.errorString = 'Erro ao obter previsão do tempo.';
				}
				$scope.errorDisplay = true;
			});
	});

	//Gets a query parameter given a name and url
	function getParameterByName(name, url) {
		if (!url) url = window.location.href;
		name = name.replace(/[\[\]]/g, "\\$&");
		var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"), results = regex.exec(url);
		if (!results) return null;
		if (!results[2]) return '';
		return decodeURIComponent(results[2].replace(/\+/g, " "));
	};
})();