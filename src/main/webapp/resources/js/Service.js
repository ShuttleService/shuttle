/**
 * Created by zorodzayi on 14/10/09.
 */
angular.module('services', ['ngResource']).

    constant('CONTEXT_ROOT', '/shuttle').

    factory('DriverService', function ($resource, CONTEXT_ROOT) {

        return $resource(CONTEXT_ROOT + '/driver/:pathVariable/:_companyId/:bookableFrom/:bookableTo/:skip/:limit', {
            pathVariable: '@pathVariable',
            _companyId: '@_companyId',
            bookingRangeFrom: '@bookableFrom',
            bookingRangeTo: '@bookableTo',
            skip: '@skip',
            limit: '@limit'
        });
    }).

    factory('TripService', function ($resource, CONTEXT_ROOT) {

        return $resource(CONTEXT_ROOT + '/trip/:skip/:limit');
    }).

    factory('ReviewService', function ($resource, CONTEXT_ROOT) {

        return $resource(CONTEXT_ROOT + '/review/:skip/:limit');
    }).

    factory('CompanyService', function ($resource, CONTEXT_ROOT) {

        return $resource(CONTEXT_ROOT + '/company/:_id/:skip/:limit', {
            _id: '@_id',
            skip: '@skip',
            limit: '@limit'
        }, {put: {method: 'put'}});
    }).

    factory('FormSubmissionUtilService', function ($log) {

        return {

            canSave: function (form) {
                $log.debug('Testing Validity For Form : ' + form + '{pristine:' + form.$pristine + ',dirty:' + form.$dirty + ',valid:' + form.$valid + ',invalid:' + form.$invalid + '}');
                return form.$valid === true && form.$dirty === true;
            }
        }
    }).

    factory('UserService', function ($resource, CONTEXT_ROOT) {

        return $resource(CONTEXT_ROOT + '/user/:subPath1/:_id/:skip/:limit', {
            subPath1: '@subPath1',
            _id: '@_id',
            skip: '@skip',
            limit: '@limit'
        }, {put: {method: 'PUT'}});
    })

    .factory('VehicleService', function ($resource, CONTEXT_ROOT) {
        return $resource(CONTEXT_ROOT + '/vehicle/:pathVariable/:_companyId/:bookableFrom/:bookableTo/:_id/:skip/:limit', {
            pathVariable: '@pathVariable',
            _companyId: '@_companyId',
            _id: '@_id',
            skip: '@skip',
            limit: '@limit',
            params: '@params',
        });
    }).

    factory('AgentService', function ($resource, CONTEXT_ROOT, $log) {
        $log.debug('Performing Some Agent Service Staff');
        return $resource(CONTEXT_ROOT + '/agent/:_id/:skip/:limit', {_id: '@_id', skip: '@skip', limit: '@limit'});
    });