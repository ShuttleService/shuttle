/**
 * Created by zorodzayi on 14/10/09.
 */
angular.module('services', ['ngResource']).

    constant('CONTEXT_ROOT', '/shuttle').

    factory('DriverService', function ($resource, CONTEXT_ROOT) {

        return $resource(CONTEXT_ROOT + '/driver/:skip/:limit', {skip: '@skip', limit: '@limit'});
    }).

    factory('TripService', function ($resource, CONTEXT_ROOT) {

        return $resource(CONTEXT_ROOT + '/trip/:skip/:limit');
    }).

    factory('ReviewService', function ($resource, CONTEXT_ROOT) {

        return $resource(CONTEXT_ROOT + '/review/:skip/:limit');
    }).

    factory('CompanyService', function ($resource, CONTEXT_ROOT) {

        return $resource(CONTEXT_ROOT + '/company/:_id/:skip/:limit', {_id: '@_id', skip: '@skip', limit: '@limit'}, {put: {method: 'put'}});
    }).

    factory('FormSubmissionUtilService', function () {

        return {

            canSave: function (form) {
                console.log('Testing Validity For Form : '+form);
                return form.$valid === true && form.$dirty === true;
            }
        }
    }).

    factory('UserService',function($resource,CONTEXT_ROOT){

       return $resource(CONTEXT_ROOT+'/user/:_id/:skip/:limit',{_id:'@_id',skip:'@skip',limit:'@limit'},{put:{method:'PUT'}});
    });