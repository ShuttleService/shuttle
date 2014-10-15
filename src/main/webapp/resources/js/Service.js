/**
 * Created by zorodzayi on 14/10/09.
 */
angular.module('services', ['ngResource']).

    constant('CONTEXT_ROOT', '/shuttle').

    factory('DriverService', function ($resource, CONTEXT_ROOT) {

        return $resource(CONTEXT_ROOT + '/driver/:skip/:limit', {skip: '@skip', limit: '@limit'});
    }).

    factory('TripService', function ($resource, CONTEXT_ROOT) {

        return $resource(CONTEXT_ROOT+'/trip/:skip/:limit');
    }).

    factory('ReviewService',function($resource,CONTEXT_ROOT){

        return $resource(CONTEXT_ROOT+'/review/:skip/:limit');
    })