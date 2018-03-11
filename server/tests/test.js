'use strict';

// This file is used for testing endpoint behavior

const chai = require('chai');
const chaiHttp = require('chai-http');
const expect = require('chai').expect;
const app = require('../server')

chai.use(chaiHttp);

describe('GET /user', function() {
  it('should return a list of users', function(done) {
    chai.request(app)
      .get('/user')
      .then(function(res) {
        expect(res).to.have.status(200);
        expect(res).to.be.json;
        done();
      });
  });
});

describe('GET /friend', function() {
  it('should return a list of friends', function(done) {
    chai.request(app)
      .get('/friend')
      .then(function(res) {
        expect(res).to.have.status(200);
        expect(res).to.be.json;
        done();
      });
  });
});