package com.arcms.biz.domain.generation.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HongbaoObtainExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    /** 在查询中增加for update 
     * 目前用来支持对查询记录加锁
     **/
    protected boolean forUpdate;

    /** 是否自定义设置查询字段 */
    protected boolean definedQueryColumns;

    /** 设置查询字段 */
    protected List<String> queryColumns;

    public HongbaoObtainExample() {
        oredCriteria = new ArrayList<Criteria>();
        queryColumns = new ArrayList<String>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
        forUpdate = false;
        definedQueryColumns = false;
        queryColumns.clear();
    }

    public void setForUpdate(boolean forUpdate) {
        this.forUpdate = forUpdate;
    }

    public boolean isForUpdate() {
        return forUpdate;
    }

    public void setDefinedQueryColumns(boolean definedQueryColumns) {
        this.definedQueryColumns = definedQueryColumns;
    }

    public boolean isDefinedQueryColumns() {
        return definedQueryColumns;
    }

    public void addIdQueryColumn() {
        this.queryColumns.add("id");
    }

    public void addGmtCreatedQueryColumn() {
        this.queryColumns.add("gmt_created");
    }

    public void addCreatedByQueryColumn() {
        this.queryColumns.add("created_by");
    }

    public void addGmtModifiedQueryColumn() {
        this.queryColumns.add("gmt_modified");
    }

    public void addModifiedByQueryColumn() {
        this.queryColumns.add("modified_by");
    }

    public void addIsDeletedQueryColumn() {
        this.queryColumns.add("is_deleted");
    }

    public void addHongbaoObtainIdQueryColumn() {
        this.queryColumns.add("hongbao_obtain_id");
    }

    public void addUserIdQueryColumn() {
        this.queryColumns.add("user_id");
    }

    public void addarcmsIconQueryColumn() {
        this.queryColumns.add("arcms_icon");
    }

    public void addHongbaoActivityIdQueryColumn() {
        this.queryColumns.add("hongbao_activity_id");
    }

    public void addHongbaoActivityNameQueryColumn() {
        this.queryColumns.add("hongbao_activity_name");
    }

    public void addObtainDatatimeQueryColumn() {
        this.queryColumns.add("obtain_datatime");
    }

    public void addStatusQueryColumn() {
        this.queryColumns.add("status");
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedIsNull() {
            addCriterion("gmt_created is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedIsNotNull() {
            addCriterion("gmt_created is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedEqualTo(Date value) {
            addCriterion("gmt_created =", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedNotEqualTo(Date value) {
            addCriterion("gmt_created <>", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedGreaterThan(Date value) {
            addCriterion("gmt_created >", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_created >=", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedLessThan(Date value) {
            addCriterion("gmt_created <", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_created <=", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedIn(List<Date> values) {
            addCriterion("gmt_created in", values, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedNotIn(List<Date> values) {
            addCriterion("gmt_created not in", values, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedBetween(Date value1, Date value2) {
            addCriterion("gmt_created between", value1, value2, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedNotBetween(Date value1, Date value2) {
            addCriterion("gmt_created not between", value1, value2, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNull() {
            addCriterion("created_by is null");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNotNull() {
            addCriterion("created_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedByEqualTo(String value) {
            addCriterion("created_by =", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotEqualTo(String value) {
            addCriterion("created_by <>", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThan(String value) {
            addCriterion("created_by >", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThanOrEqualTo(String value) {
            addCriterion("created_by >=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThan(String value) {
            addCriterion("created_by <", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThanOrEqualTo(String value) {
            addCriterion("created_by <=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLike(String value) {
            addCriterion("created_by like", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotLike(String value) {
            addCriterion("created_by not like", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByIn(List<String> values) {
            addCriterion("created_by in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotIn(List<String> values) {
            addCriterion("created_by not in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByBetween(String value1, String value2) {
            addCriterion("created_by between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotBetween(String value1, String value2) {
            addCriterion("created_by not between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNull() {
            addCriterion("gmt_modified is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNotNull() {
            addCriterion("gmt_modified is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedEqualTo(Date value) {
            addCriterion("gmt_modified =", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotEqualTo(Date value) {
            addCriterion("gmt_modified <>", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThan(Date value) {
            addCriterion("gmt_modified >", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_modified >=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThan(Date value) {
            addCriterion("gmt_modified <", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_modified <=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIn(List<Date> values) {
            addCriterion("gmt_modified in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotIn(List<Date> values) {
            addCriterion("gmt_modified not in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedBetween(Date value1, Date value2) {
            addCriterion("gmt_modified between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotBetween(Date value1, Date value2) {
            addCriterion("gmt_modified not between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andModifiedByIsNull() {
            addCriterion("modified_by is null");
            return (Criteria) this;
        }

        public Criteria andModifiedByIsNotNull() {
            addCriterion("modified_by is not null");
            return (Criteria) this;
        }

        public Criteria andModifiedByEqualTo(String value) {
            addCriterion("modified_by =", value, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedByNotEqualTo(String value) {
            addCriterion("modified_by <>", value, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedByGreaterThan(String value) {
            addCriterion("modified_by >", value, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedByGreaterThanOrEqualTo(String value) {
            addCriterion("modified_by >=", value, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedByLessThan(String value) {
            addCriterion("modified_by <", value, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedByLessThanOrEqualTo(String value) {
            addCriterion("modified_by <=", value, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedByLike(String value) {
            addCriterion("modified_by like", value, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedByNotLike(String value) {
            addCriterion("modified_by not like", value, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedByIn(List<String> values) {
            addCriterion("modified_by in", values, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedByNotIn(List<String> values) {
            addCriterion("modified_by not in", values, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedByBetween(String value1, String value2) {
            addCriterion("modified_by between", value1, value2, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedByNotBetween(String value1, String value2) {
            addCriterion("modified_by not between", value1, value2, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Boolean value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Boolean value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Boolean value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Boolean value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Boolean> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Boolean> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andHongbaoObtainIdIsNull() {
            addCriterion("hongbao_obtain_id is null");
            return (Criteria) this;
        }

        public Criteria andHongbaoObtainIdIsNotNull() {
            addCriterion("hongbao_obtain_id is not null");
            return (Criteria) this;
        }

        public Criteria andHongbaoObtainIdEqualTo(Long value) {
            addCriterion("hongbao_obtain_id =", value, "hongbaoObtainId");
            return (Criteria) this;
        }

        public Criteria andHongbaoObtainIdNotEqualTo(Long value) {
            addCriterion("hongbao_obtain_id <>", value, "hongbaoObtainId");
            return (Criteria) this;
        }

        public Criteria andHongbaoObtainIdGreaterThan(Long value) {
            addCriterion("hongbao_obtain_id >", value, "hongbaoObtainId");
            return (Criteria) this;
        }

        public Criteria andHongbaoObtainIdGreaterThanOrEqualTo(Long value) {
            addCriterion("hongbao_obtain_id >=", value, "hongbaoObtainId");
            return (Criteria) this;
        }

        public Criteria andHongbaoObtainIdLessThan(Long value) {
            addCriterion("hongbao_obtain_id <", value, "hongbaoObtainId");
            return (Criteria) this;
        }

        public Criteria andHongbaoObtainIdLessThanOrEqualTo(Long value) {
            addCriterion("hongbao_obtain_id <=", value, "hongbaoObtainId");
            return (Criteria) this;
        }

        public Criteria andHongbaoObtainIdIn(List<Long> values) {
            addCriterion("hongbao_obtain_id in", values, "hongbaoObtainId");
            return (Criteria) this;
        }

        public Criteria andHongbaoObtainIdNotIn(List<Long> values) {
            addCriterion("hongbao_obtain_id not in", values, "hongbaoObtainId");
            return (Criteria) this;
        }

        public Criteria andHongbaoObtainIdBetween(Long value1, Long value2) {
            addCriterion("hongbao_obtain_id between", value1, value2, "hongbaoObtainId");
            return (Criteria) this;
        }

        public Criteria andHongbaoObtainIdNotBetween(Long value1, Long value2) {
            addCriterion("hongbao_obtain_id not between", value1, value2, "hongbaoObtainId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andarcmsIconIsNull() {
            addCriterion("arcms_icon is null");
            return (Criteria) this;
        }

        public Criteria andarcmsIconIsNotNull() {
            addCriterion("arcms_icon is not null");
            return (Criteria) this;
        }

        public Criteria andarcmsIconEqualTo(Long value) {
            addCriterion("arcms_icon =", value, "arcmsIcon");
            return (Criteria) this;
        }

        public Criteria andarcmsIconNotEqualTo(Long value) {
            addCriterion("arcms_icon <>", value, "arcmsIcon");
            return (Criteria) this;
        }

        public Criteria andarcmsIconGreaterThan(Long value) {
            addCriterion("arcms_icon >", value, "arcmsIcon");
            return (Criteria) this;
        }

        public Criteria andarcmsIconGreaterThanOrEqualTo(Long value) {
            addCriterion("arcms_icon >=", value, "arcmsIcon");
            return (Criteria) this;
        }

        public Criteria andarcmsIconLessThan(Long value) {
            addCriterion("arcms_icon <", value, "arcmsIcon");
            return (Criteria) this;
        }

        public Criteria andarcmsIconLessThanOrEqualTo(Long value) {
            addCriterion("arcms_icon <=", value, "arcmsIcon");
            return (Criteria) this;
        }

        public Criteria andarcmsIconIn(List<Long> values) {
            addCriterion("arcms_icon in", values, "arcmsIcon");
            return (Criteria) this;
        }

        public Criteria andarcmsIconNotIn(List<Long> values) {
            addCriterion("arcms_icon not in", values, "arcmsIcon");
            return (Criteria) this;
        }

        public Criteria andarcmsIconBetween(Long value1, Long value2) {
            addCriterion("arcms_icon between", value1, value2, "arcmsIcon");
            return (Criteria) this;
        }

        public Criteria andarcmsIconNotBetween(Long value1, Long value2) {
            addCriterion("arcms_icon not between", value1, value2, "arcmsIcon");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityIdIsNull() {
            addCriterion("hongbao_activity_id is null");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityIdIsNotNull() {
            addCriterion("hongbao_activity_id is not null");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityIdEqualTo(Long value) {
            addCriterion("hongbao_activity_id =", value, "hongbaoActivityId");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityIdNotEqualTo(Long value) {
            addCriterion("hongbao_activity_id <>", value, "hongbaoActivityId");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityIdGreaterThan(Long value) {
            addCriterion("hongbao_activity_id >", value, "hongbaoActivityId");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityIdGreaterThanOrEqualTo(Long value) {
            addCriterion("hongbao_activity_id >=", value, "hongbaoActivityId");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityIdLessThan(Long value) {
            addCriterion("hongbao_activity_id <", value, "hongbaoActivityId");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityIdLessThanOrEqualTo(Long value) {
            addCriterion("hongbao_activity_id <=", value, "hongbaoActivityId");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityIdIn(List<Long> values) {
            addCriterion("hongbao_activity_id in", values, "hongbaoActivityId");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityIdNotIn(List<Long> values) {
            addCriterion("hongbao_activity_id not in", values, "hongbaoActivityId");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityIdBetween(Long value1, Long value2) {
            addCriterion("hongbao_activity_id between", value1, value2, "hongbaoActivityId");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityIdNotBetween(Long value1, Long value2) {
            addCriterion("hongbao_activity_id not between", value1, value2, "hongbaoActivityId");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityNameIsNull() {
            addCriterion("hongbao_activity_name is null");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityNameIsNotNull() {
            addCriterion("hongbao_activity_name is not null");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityNameEqualTo(String value) {
            addCriterion("hongbao_activity_name =", value, "hongbaoActivityName");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityNameNotEqualTo(String value) {
            addCriterion("hongbao_activity_name <>", value, "hongbaoActivityName");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityNameGreaterThan(String value) {
            addCriterion("hongbao_activity_name >", value, "hongbaoActivityName");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityNameGreaterThanOrEqualTo(String value) {
            addCriterion("hongbao_activity_name >=", value, "hongbaoActivityName");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityNameLessThan(String value) {
            addCriterion("hongbao_activity_name <", value, "hongbaoActivityName");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityNameLessThanOrEqualTo(String value) {
            addCriterion("hongbao_activity_name <=", value, "hongbaoActivityName");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityNameLike(String value) {
            addCriterion("hongbao_activity_name like", value, "hongbaoActivityName");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityNameNotLike(String value) {
            addCriterion("hongbao_activity_name not like", value, "hongbaoActivityName");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityNameIn(List<String> values) {
            addCriterion("hongbao_activity_name in", values, "hongbaoActivityName");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityNameNotIn(List<String> values) {
            addCriterion("hongbao_activity_name not in", values, "hongbaoActivityName");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityNameBetween(String value1, String value2) {
            addCriterion("hongbao_activity_name between", value1, value2, "hongbaoActivityName");
            return (Criteria) this;
        }

        public Criteria andHongbaoActivityNameNotBetween(String value1, String value2) {
            addCriterion("hongbao_activity_name not between", value1, value2, "hongbaoActivityName");
            return (Criteria) this;
        }

        public Criteria andObtainDatatimeIsNull() {
            addCriterion("obtain_datatime is null");
            return (Criteria) this;
        }

        public Criteria andObtainDatatimeIsNotNull() {
            addCriterion("obtain_datatime is not null");
            return (Criteria) this;
        }

        public Criteria andObtainDatatimeEqualTo(Date value) {
            addCriterion("obtain_datatime =", value, "obtainDatatime");
            return (Criteria) this;
        }

        public Criteria andObtainDatatimeNotEqualTo(Date value) {
            addCriterion("obtain_datatime <>", value, "obtainDatatime");
            return (Criteria) this;
        }

        public Criteria andObtainDatatimeGreaterThan(Date value) {
            addCriterion("obtain_datatime >", value, "obtainDatatime");
            return (Criteria) this;
        }

        public Criteria andObtainDatatimeGreaterThanOrEqualTo(Date value) {
            addCriterion("obtain_datatime >=", value, "obtainDatatime");
            return (Criteria) this;
        }

        public Criteria andObtainDatatimeLessThan(Date value) {
            addCriterion("obtain_datatime <", value, "obtainDatatime");
            return (Criteria) this;
        }

        public Criteria andObtainDatatimeLessThanOrEqualTo(Date value) {
            addCriterion("obtain_datatime <=", value, "obtainDatatime");
            return (Criteria) this;
        }

        public Criteria andObtainDatatimeIn(List<Date> values) {
            addCriterion("obtain_datatime in", values, "obtainDatatime");
            return (Criteria) this;
        }

        public Criteria andObtainDatatimeNotIn(List<Date> values) {
            addCriterion("obtain_datatime not in", values, "obtainDatatime");
            return (Criteria) this;
        }

        public Criteria andObtainDatatimeBetween(Date value1, Date value2) {
            addCriterion("obtain_datatime between", value1, value2, "obtainDatatime");
            return (Criteria) this;
        }

        public Criteria andObtainDatatimeNotBetween(Date value1, Date value2) {
            addCriterion("obtain_datatime not between", value1, value2, "obtainDatatime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}