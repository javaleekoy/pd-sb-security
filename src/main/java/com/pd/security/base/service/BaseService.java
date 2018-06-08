package com.pd.security.base.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author peramdy on 2018/6/2.
 *         service扩展
 */
@Transactional(readOnly = true, rollbackFor = Exception.class)
public abstract class BaseService {
}
