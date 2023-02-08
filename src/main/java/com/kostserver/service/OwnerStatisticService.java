package com.kostserver.service;

import java.util.Map;

public interface OwnerStatisticService {
    Map<String, Object> ownerStatistic(String email) throws Exception;
}