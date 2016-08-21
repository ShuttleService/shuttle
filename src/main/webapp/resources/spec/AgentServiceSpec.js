/**
 * Created by zorodzayi on 14/12/17.
 */
describe('Testing The Agent Service', function () {
    var AgentService, $httpBackend;

    beforeEach(module('services'));
    beforeEach(inject(function (_AgentService_,_$httpBackend_) {
        AgentService = _AgentService_;
        $httpBackend = _$httpBackend_;

        expect(AgentService).toBeDefined();

    }));


    it('Save,Get,Query Methods Are Defined',function(){
        expect(AgentService.get).toBeDefined();
        expect(AgentService.save).toBeDefined();
        expect(AgentService.query).toBeDefined();
    });

    it('Call The Correct URL To Save The Agent',function(){

        var agent = {name:'Test Agent Name To Be Saved'};
        $httpBackend.expectPOST('/agent',agent).respond(agent);

        var actual = AgentService.save(agent);
        $httpBackend.flush();
        expect(actual.name).toEqual(agent.name);

        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    it('Call The Correct URL To Return The Page',function(){
        var agent = {name:'Test Agent Name For The Agent To Be Returned On The Page'};
        var page = {content:[agent]};
        var skip = 0, limit = 2;
        var params = {skip:skip,limit:limit};

        $httpBackend.expectGET('/agent/'+skip+'/'+limit).respond(page);

        var actual = AgentService.get(params);
        $httpBackend.flush();
        expect(actual.content[0].name).toEqual(agent.name);
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });
});