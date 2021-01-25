-- ----------------------------
-- Table structure for t_report_task_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_report_task_info";
CREATE TABLE  "public"."t_report_task_info"
(
    "id"          varchar COLLATE "pg_catalog"."default" NOT NULL,
    "user_id" varchar(32) COLLATE "pg_catalog"."default",
    "report_ids"  text COLLATE "pg_catalog"."default",
    "name"  varchar(255) COLLATE "pg_catalog"."default",
    "cycle_type"  varchar(255) COLLATE "pg_catalog"."default",
    "status"      varchar(255) COLLATE "pg_catalog"."default",
    "remark"      varchar(255) COLLATE "pg_catalog"."default",
    "create_time" timestamp(6),
    "update_time" timestamp(6),
    "params"      text COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."t_report_task_info"."report_ids" IS '模板id';
COMMENT ON COLUMN "public"."t_report_task_info"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."t_report_task_info"."cycle_type" IS '周期类型';
COMMENT ON COLUMN "public"."t_report_task_info"."name" IS '任务名称';
COMMENT ON COLUMN "public"."t_report_task_info"."status" IS '任务状态';
COMMENT ON COLUMN "public"."t_report_task_info"."remark" IS '描述';
COMMENT ON COLUMN "public"."t_report_task_info"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."t_report_task_info"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."t_report_task_info"."params" IS '参数（json）';

-- ----------------------------
-- Primary Key structure for table t_report_task_info
-- ----------------------------
ALTER TABLE "public"."t_report_task_info"
    ADD CONSTRAINT "t_task_info_pkey" PRIMARY KEY ("id");
