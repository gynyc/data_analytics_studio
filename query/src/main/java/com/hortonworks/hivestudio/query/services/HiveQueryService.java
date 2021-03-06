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
package com.hortonworks.hivestudio.query.services;

import java.io.InputStream;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.commons.lang3.StringUtils;
import com.hortonworks.hivestudio.common.exception.generic.ConstraintViolationException;
import com.hortonworks.hivestudio.common.exception.generic.ItemNotFoundException;
import com.hortonworks.hivestudio.common.entities.HiveQuery;
import com.hortonworks.hivestudio.query.entities.repositories.HiveQueryRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service to interact with the hive queries
 */
@Slf4j
public class HiveQueryService {
  private final Provider<HiveQueryRepository> repositoryProvider;

  @Inject
  public HiveQueryService(Provider<HiveQueryRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  public HiveQuery getOne(Long id) {
    HiveQueryRepository repository = repositoryProvider.get();
    Optional<HiveQuery> queryOptional = repository.findOne(id);
    queryOptional.orElseThrow(() -> new ItemNotFoundException("Hive Query with id '" + id + "' not found"));
    return  queryOptional.get();
  }


  public HiveQuery getOneByHiveQueryId(String id) {
    HiveQueryRepository repository = repositoryProvider.get();
    Optional<HiveQuery> hiveQueryInfo = repository.findByHiveQueryId(id);
    Optional<HiveQuery> hiveQueryOptional = hiveQueryInfo;
    hiveQueryOptional.orElseThrow(() -> new ItemNotFoundException("Hive Query with query id '" + id + "' not found"));
    return hiveQueryOptional.get();
  }

  public InputStream getDownloadAllStream(String id) {
    if(StringUtils.isEmpty(id)) {
      log.error("Cannot download all logs for empty hive queryId");
      throw new ConstraintViolationException("Hive Query id cannot be null or empty");
    }

    ClassLoader classLoader = getClass().getClassLoader();
    return classLoader.getResourceAsStream("banner.txt");
    //TODO: Actual implementation
  }
}
