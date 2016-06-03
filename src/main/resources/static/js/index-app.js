(function () {
	var app = angular.module('myApp', []);

	app.controller('myCtrl', function ($scope, $http) {
		$scope.city = 'Curitiba';
		$scope.errorDisplay = false;
		$http.get('rest/cidades-cadastradas')
			.then(function (response) {
				$scope.cities = response.data;
			});

		//Posts the city for registering
		$scope.submitCity = function () {
			$http.post('rest/cadastrar-cidade', $scope.cityName)
				.then(function (response) {
					$scope.errorDisplay = false;
					$scope.cityName = '';
					$scope.cities = response.data;
				}, function (response) {
					$scope.errorDisplay = true;
					$scope.cityName = '';
					if (response.status == 406) {
						$scope.errorString = 'Nome de cidade inválido!'
					} else if (response.status == 400) {
						$scope.errorString = 'Nome não preenchido.'
					} else if (response.status == 409) {
						$scope.errorString = 'Cidade já registrada.'
					}
				});
		};

		//Allows enter to pressed on the main input field
		$scope.processEnter = function ($event) {
			if ($event.keyCode == 13) {
				$scope.submitCity();
			}
		};
	});
})();