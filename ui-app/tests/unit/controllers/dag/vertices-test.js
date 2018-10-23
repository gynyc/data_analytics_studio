/*
 *
 * HORTONWORKS DATAPLANE SERVICE AND ITS CONSTITUENT SERVICES
 *
 * (c) 2016-2018 Hortonworks, Inc. All rights reserved.
 *
 * This code is provided to you pursuant to your written agreement with Hortonworks, which may be the terms of the
 * Affero General Public License version 3 (AGPLv3), or pursuant to a written agreement with a third party authorized
 * to distribute this code.  If you do not have a written agreement with Hortonworks or with an authorized and
 * properly licensed third party, you do not have any rights to this code.
 *
 * If this code is provided to you under the terms of the AGPLv3:
 * (A) HORTONWORKS PROVIDES THIS CODE TO YOU WITHOUT WARRANTIES OF ANY KIND;
 * (B) HORTONWORKS DISCLAIMS ANY AND ALL EXPRESS AND IMPLIED WARRANTIES WITH RESPECT TO THIS CODE, INCLUDING BUT NOT
 *   LIMITED TO IMPLIED WARRANTIES OF TITLE, NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE;
 * (C) HORTONWORKS IS NOT LIABLE TO YOU, AND WILL NOT DEFEND, INDEMNIFY, OR HOLD YOU HARMLESS FOR ANY CLAIMS ARISING
 *   FROM OR RELATED TO THE CODE; AND
 * (D) WITH RESPECT TO YOUR EXERCISE OF ANY RIGHTS GRANTED TO YOU FOR THE CODE, HORTONWORKS IS NOT LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, PUNITIVE OR CONSEQUENTIAL DAMAGES INCLUDING, BUT NOT LIMITED TO,
 *   DAMAGES RELATED TO LOST REVENUE, LOST PROFITS, LOSS OF INCOME, LOSS OF BUSINESS ADVANTAGE OR UNAVAILABILITY,
 *   OR LOSS OR CORRUPTION OF DATA.
 *
 */

import Ember from 'ember';

import { moduleFor, test } from 'ember-qunit';

moduleFor('controller:dag/vertices', 'Unit | Controller | dag/vertices', {
  // Specify the other units that are required for this test.
  // needs: ['controller:foo']
});

test('Basic creation test', function(assert) {
  let controller = this.subject({
    send: Ember.K,
    beforeSort: {bind: Ember.K},
    initVisibleColumns: Ember.K,
    getCounterColumns: function () {
      return [];
    }
  });

  assert.ok(controller);
  assert.ok(controller.breadcrumbs);
  assert.ok(controller.columns);
  assert.ok(controller.beforeSort);
});

test('beforeSort test', function(assert) {
  let controller = this.subject({
    initVisibleColumns: Ember.K,
    getCounterColumns: function () {
      return [];
    },
    polling: {
      isReady: true
    },
    send: function (actionName) {
      if(actionName === "openModal") {
        assert.ok(true);
      }
    }
  });

  // Bind poilyfill
  Function.prototype.bind = function (context) {
    var that = this;
    return function (val) {
      return that.call(context, val);
    };
  };

  assert.expect(1 + 3 + 3);

  assert.ok(controller.beforeSort(Ember.Object.create({
    contentPath: "NonDisabledColumn"
  })), "NonDisabledColumn");

  assert.notOk(controller.beforeSort(Ember.Object.create({
    contentPath: "succeededTasks"
  })), "succeededTasks");
  assert.notOk(controller.beforeSort(Ember.Object.create({
    contentPath: "runningTasks"
  })), "runningTasks");
  assert.notOk(controller.beforeSort(Ember.Object.create({
    contentPath: "pendingTasks"
  })), "pendingTasks");

});