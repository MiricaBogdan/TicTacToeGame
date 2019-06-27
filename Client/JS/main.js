var app = angular.module("TicTacToeApp",['ngRoute','LocalStorageModule','ngStomp']);

app.config(function($routeProvider) {
    $routeProvider
        .when("/", {
        templateUrl : "Contents/Content.html",
    })
        .when("/register", {
        templateUrl : "Contents/Register.html"
    })
        .when("/login", {
        templateUrl : "Contents/Login.html"
    })
        .when("/start", {
        templateUrl : "Contents/StartQueue.html"
    })
        .when("/search", {
        templateUrl : "Contents/Searchingforgame.html"
    })
        .when("/game",{
        templateUrl : "Contents/Game.html"
    });
});

app.controller('registerController', function($scope,$location,$http) {

    $scope.register=function(){
        if($scope.name==null){
            alert("Please enter your name");
        }
        else if($scope.username==null){
            alert("Please enter your username");
        }
        else if($scope.password==null){
            alert("Please enter your password");
        }
        else{
            var registerRequest = {
                method: 'POST',
                url: 'http://192.168.1.103:8080/user/add',
                headers: {
                    'Content-Type': 'application/json'
                },
                data:{ 
                    name:$scope.name,
                    username:$scope.username,
                    password:$scope.password
                }
            }
            $http(registerRequest).then(function succes(response) {
                if(response.data==false){
                    alert("This username already exists");
                }
                else{
                    $location.path ('/login');
                    alert("Your registration was succesful");
                }
            }, function error() {
                alert("Something went wrong");
            });
        }}

});

app.controller('loginController', function($scope,$location,$http,localStorageService) {

    $scope.login=function(){
        if($scope.username==null){
            alert("Please enter your username");
        }
        else if($scope.password==null){
            alert("Please enter your password");
        }
        else{
            var loginRequest = {
                method: 'POST',
                url: 'http://192.168.1.103:8080/user/login',
                headers: {
                    'Content-Type': 'application/json'
                },
                data:{ 
                    username:$scope.username,
                    password:$scope.password
                }
            }
            $http(loginRequest).then(function succes(response) {
                if(response.data.length==0){
                    alert("Incorect Username or Password");
                }
                else{
                    localStorageService.set('name',response.data.name);
                    localStorageService.set('gameswon',response.data.gameswon);
                    localStorageService.set('id',response.data.id);
                    $location.path('/start');
                }
            }, function error() {
                alert("Something went wrong");
            });
        }}

});

app.controller('startController', function($scope,$location,$http,localStorageService,checkLoginService) {
    $scope.checkLogin=function(){
        if(checkLoginService.checkIfLogin()==false)
        {
            $location.path('/login');
        }
    }
    $scope.name=localStorageService.get('name');
    $scope.startQueue=function(){
        $location.path('/search');
    }
    $scope.logOut=function(){
        localStorageService.clearAll();
        $location.path('/');
    }
});

app.controller('searchController', function($scope,$location,$http,localStorageService,$stomp,checkLoginService) {
    $scope.checkLogin=function(){
        if(checkLoginService.checkIfLogin()==false)
        {
            $location.path('/login');
        }
    }
    var subscription;
    $scope.name=localStorageService.get('name');
    $scope.gameswon=localStorageService.get('gameswon');
    $scope.id=localStorageService.get('id');

    /*
    var stompClient = null;

    connect();


    $scope.sendMesage = function(){
        console.log("ab");
        stompClient.send("/app/search", {}, JSON.stringify({'name':"name"}));
        console.log("ac");
    }


    function connect() {
        var socket = new SockJS('http://192.168.1.103:8080/gs-guide-websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/queue', function (greeting) {
                showGreeting(JSON.parse(greeting.body).content);
            });
        });
    }
    */

    $stomp
        .connect('http://192.168.1.103:8080/gs-guide-websocket', {})
        .then(function (frame) {
        console.log("Connected " +frame);
        subscription= $stomp.subscribe('/topic/queue/', function (payload, headers, res) {
            if(payload != null)
            {
                $stomp.unsubscribe(subscription);
                $stomp.disconnect().then(function () {
                    console.log("disconnected");
                });
                localStorageService.set('room',payload.room);
                if(payload.player1.id==$scope.id){
                    localStorageService.set('opponentName',payload.player2.name);
                    localStorageService.set('opponentGameswon',payload.player2.gameswon);
                    localStorageService.set("turn",1);
                    localStorageService.set("player",1)
                    //alert("Game found \nYou start first!");

                }
                else{
                    localStorageService.set('opponentName',payload.player1.name);
                    localStorageService.set('opponentGameswon',payload.player1.gameswon);
                    localStorageService.set("turn",0);
                    localStorageService.set("player",2)
                    //alert("Game found \nYour opponent start first!");
                }
                $location.path('/game');
                $scope.$apply();

            }
        }, {})
        $stomp.send("/app/search",{'name':$scope.name,'gameswon':$scope.gameswon,'id':$scope.id },{});
    })

});

app.controller('gameController', function($scope,$location,$http,localStorageService,$stomp,checkLoginService) {
    $scope.checkOpponent=function(){
        if(checkLoginService.checkIfLogin()==false)
        {
            $location.path('/login');
        }
        else{
            if(localStorageService.get('opponentName')==null){
                $location.path('/search'); 
            }
        }
    }
    $scope.name=localStorageService.get('name');
    $scope.gameswon=localStorageService.get('gameswon');
    $scope.opponentName=localStorageService.get('opponentName');
    $scope.opponentGameswon=localStorageService.get('opponentGameswon');
    $scope.room=localStorageService.get('room');
    $scope.turn=localStorageService.get('turn');
    var player=localStorageService.get('player');
    var subscription;
    $scope.matrixValue=[];
    var i;
    var j;

    for(i=0; i<3; i++) {
        $scope.matrixValue[i] = [];
        for(j=0; j<3; j++) {
            $scope.matrixValue[i][j]="";
        }
    }

    $stomp
        .connect('http://192.168.1.103:8080/gs-guide-websocket', {})
        .then(function (frame) {
        console.log("Connected " +frame);
        $scope.showPlayerTurn();
        subscription= $stomp.subscribe('/topic/game/'+$scope.room, function (payload, headers, res) {
            $scope.changeMatrixValues(payload);
            changePlayerTurn(payload);
            $scope.showPlayerTurn(payload);
            console.log(payload);
            $scope.$apply();
            setTimeout(function(){
                $scope.checkWinner(payload);},100);

        }, {})
    })

    $scope.clickedBox=function(event){
        //  alert(event.target.id);

        if(checkPlayerTurn()==true){
            var move=parseInt(event.target.id);
            j=parseInt(move%10);
            i=parseInt(move/10);

            if(player==1){ 
                if($scope.matrixValue[i][j]==""){
                    $stomp.send("/app/game/"+$scope.room,{move:move},{});
                    $scope.matrixValue[i][j]="X";
                    $scope.turn=0;
                }
            }
            if(player==2){
                if($scope.matrixValue[i][j]==""){
                    $stomp.send("/app/game/"+$scope.room,{move:move},{});
                    $scope.matrixValue[i][j]="O";      
                    $scope.turn=0;
                }
            }
        }
    }

    function checkPlayerTurn(){
        if($scope.turn==1){
            return true;
        }
        return false;
    }

    $scope.changeMatrixValues=function(gameDto){
        for(i=0; i<3; i++) {
            for(j=0; j<3; j++) {
                if(gameDto.matrix[i][j]==1){
                    $scope.matrixValue[i][j]="X";
                }
                if(gameDto.matrix[i][j]==0){
                    $scope.matrixValue[i][j]="O";
                }
            }
        }
    }

    function changePlayerTurn(gameDto){
        if((gameDto.status==2)&&(player==1)){
            $scope.turn=1;
        }
        if((gameDto.status==3)&&(player==2)){
            $scope.turn=1;
        }
    }

    $scope.showPlayerTurn=function(GameDto){
        if((GameDto!=null)&&(GameDto.status==4)){
            $scope.PlayerTurn="Game over !";
        }
        else if(checkPlayerTurn()==true){
            $scope.PlayerTurn=$scope.name +" turn!";
        }
        else{
            $scope.PlayerTurn=$scope.opponentName+" turn!";
        }
    }

    $scope.checkWinner=function(gameDto){
        if(gameDto.status==4){
            if(gameDto.playerWon==null){
                alert("Draw game!");
                $location.path('/start');
                $scope.$apply();
            }
            else if(gameDto.playerWon.name==$scope.name){
                alert("You won!");
                $location.path('/start');
                $scope.$apply();
            }
            else{
                alert("Your opponent won!");
                $location.path('/start');
                $scope.$apply();
            }
        }
    }

});

app.directive("imgPanel", function() {
  return {
    templateUrl: './Directives/imgPanel.html'
  };
});

app.service('checkLoginService', function(localStorageService,$location) {

    this.checkIfLogin=function(){
        if(localStorageService.length()==0){
            return false;
        }
        else{
            return true;
        }
    }
});

