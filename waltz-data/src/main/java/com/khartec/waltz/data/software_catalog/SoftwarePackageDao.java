/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019 Waltz open source project
 * See README.md for more information
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific
 *
 */

package com.khartec.waltz.data.software_catalog;

import com.khartec.waltz.data.JooqUtilities;
import com.khartec.waltz.data.SearchDao;
import com.khartec.waltz.model.NameProvider;
import com.khartec.waltz.model.UserTimestamp;
import com.khartec.waltz.model.entity_search.EntitySearchOptions;
import com.khartec.waltz.model.software_catalog.ImmutableSoftwarePackage;
import com.khartec.waltz.model.software_catalog.SoftwarePackage;
import com.khartec.waltz.model.tally.Tally;
import com.khartec.waltz.schema.tables.records.SoftwarePackageRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.khartec.waltz.common.Checks.checkNotNull;
import static com.khartec.waltz.common.SetUtilities.orderedUnion;
import static com.khartec.waltz.data.JooqUtilities.mkBasicTermSearch;
import static com.khartec.waltz.data.JooqUtilities.mkStartsWithTermSearch;
import static com.khartec.waltz.data.SearchUtilities.mkRelevancyComparator;
import static com.khartec.waltz.data.SearchUtilities.mkTerms;
import static com.khartec.waltz.schema.tables.SoftwarePackage.SOFTWARE_PACKAGE;
import static com.khartec.waltz.schema.tables.SoftwareUsage.SOFTWARE_USAGE;
import static com.khartec.waltz.schema.tables.SoftwareVersion.SOFTWARE_VERSION;

@Repository
public class SoftwarePackageDao implements SearchDao<SoftwarePackage> {

    private static final RecordMapper<Record, SoftwarePackage> TO_DOMAIN = r -> {
        SoftwarePackageRecord record = r.into(SOFTWARE_PACKAGE);
        return ImmutableSoftwarePackage.builder()
                .id(record.getId())
                .vendor(record.getVendor())
                .group(record.getGroup())
                .name(record.getName())
                .isNotable(record.getNotable())
                .externalId(Optional.ofNullable(record.getExternalId()))
                .description(record.getDescription())
                .created(UserTimestamp.mkForUser(record.getCreatedBy(), record.getCreatedAt()))
                .provenance(record.getProvenance())
                .build();
    };


    private final DSLContext dsl;

    @Autowired
    public SoftwarePackageDao(DSLContext dsl) {
        this.dsl = dsl;
    }


    public List<SoftwarePackage> findByIds(Long... ids) {
        return findByCondition(SOFTWARE_PACKAGE.ID.in(ids));
    }


    public List<SoftwarePackage> findByIds(Collection<Long> ids) {
        return findByCondition(SOFTWARE_PACKAGE.ID.in(ids));
    }


    public SoftwarePackage getById(long id) {
        return dsl.select(SOFTWARE_PACKAGE.fields())
                .from(SOFTWARE_PACKAGE)
                .where(SOFTWARE_PACKAGE.ID.eq(id))
                .fetchOne(TO_DOMAIN);
    }

    public List<SoftwarePackage> findAll() {
        return findByCondition(DSL.trueCondition());
    }

    // -----

    private List<SoftwarePackage> findByCondition(Condition condition) {
        return dsl.select(SOFTWARE_PACKAGE.fields())
                .from(SOFTWARE_PACKAGE)
                .where(condition)
                .fetch(TO_DOMAIN);
    }


    public List<Tally<String>> toTallies(Select<Record1<Long>> appIdSelector, Field<String> groupingField) {

        Condition condition = SOFTWARE_USAGE.APPLICATION_ID.in(appIdSelector);

        return dsl
                .select(groupingField, DSL.count(groupingField))
                .from(SOFTWARE_PACKAGE)
                .innerJoin(SOFTWARE_VERSION)
                    .on(SOFTWARE_VERSION.SOFTWARE_PACKAGE_ID.eq(SOFTWARE_PACKAGE.ID))
                .innerJoin(SOFTWARE_USAGE)
                    .on(SOFTWARE_USAGE.ID.eq(SOFTWARE_VERSION.SOFTWARE_PACKAGE_ID))
                .where(dsl.renderInlined(condition))
                .groupBy(groupingField)
                .fetch(JooqUtilities.TO_STRING_TALLY);
    }


    @Override
    public List<SoftwarePackage> search(EntitySearchOptions options) {
        checkNotNull(options, "options cannot be null");
        List<String> terms = mkTerms(options.searchQuery());

        if (terms.isEmpty()) {
            return Collections.emptyList();
        }

        Condition externalIdCondition = mkStartsWithTermSearch(SOFTWARE_PACKAGE.EXTERNAL_ID, terms);

        List<SoftwarePackage> serversViaExternalId = dsl
                .select(SOFTWARE_PACKAGE.fields())
                .from(SOFTWARE_PACKAGE)
                .where(externalIdCondition)
                .orderBy(SOFTWARE_PACKAGE.EXTERNAL_ID)
                .limit(options.limit())
                .fetch(SoftwarePackageDao.TO_DOMAIN);

        Condition nameCondition = mkBasicTermSearch(SOFTWARE_PACKAGE.NAME, terms);

        List<SoftwarePackage> softwareViaName = dsl
                .select(SOFTWARE_PACKAGE.fields())
                .from(SOFTWARE_PACKAGE)
                .where(nameCondition)
                .orderBy(SOFTWARE_PACKAGE.NAME)
                .limit(options.limit())
                .fetch(SoftwarePackageDao.TO_DOMAIN);

        Condition vendorCondition = mkBasicTermSearch(SOFTWARE_PACKAGE.VENDOR, terms);

        List<SoftwarePackage> softwareViaVendor = dsl
                .select(SOFTWARE_PACKAGE.fields())
                .from(SOFTWARE_PACKAGE)
                .where(vendorCondition)
                .orderBy(SOFTWARE_PACKAGE.VENDOR)
                .limit(options.limit())
                .fetch(SoftwarePackageDao.TO_DOMAIN);

        softwareViaName.sort(mkRelevancyComparator(NameProvider::name, terms.get(0)));
        softwareViaVendor.sort(mkRelevancyComparator(SoftwarePackage::vendor, terms.get(0)));
        serversViaExternalId.sort(mkRelevancyComparator(a -> a.externalId().orElse(null), terms.get(0)));

        return new ArrayList<>(orderedUnion(serversViaExternalId, softwareViaName, softwareViaVendor));
    }
}
