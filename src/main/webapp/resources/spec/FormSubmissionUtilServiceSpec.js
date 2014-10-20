/**
 * Created by reality on 14/10/19.
 */
describe('Form Submission Util Service Test', function () {

    var FormSubmissionService;
    beforeEach(module('services'));

    beforeEach(inject(function (_FormSubmissionUtilService_) {

        FormSubmissionService = _FormSubmissionUtilService_;

        expect(FormSubmissionService).toBeDefined();
    }));

    it('Should Return False When addForm is Dirty And Invalid', function () {

        var addForm = {$dirty: true, $invalid: true};
        expect(FormSubmissionService.canSave(addForm)).toEqual(false);
    });

    it('Should Return True When addForm is Dirty And Valid ', function () {

        var addForm = {$dirty: true, $valid: true};
        expect(FormSubmissionService.canSave(addForm)).toEqual(true);
    });
});