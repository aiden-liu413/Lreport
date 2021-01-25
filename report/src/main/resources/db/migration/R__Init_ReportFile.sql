-- ----------------------------
-- Table structure for t_report_file_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_report_file_info";
CREATE TABLE  "public"."t_report_file_info" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "templte_id" varchar(32) COLLATE "pg_catalog"."default",
  "user_id" varchar(32) COLLATE "pg_catalog"."default",
  "task_id" varchar(32) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "mapping_name" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "status" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."t_report_file_info"."name" IS '文件的名称（根据模板+时间段参数）';
COMMENT ON COLUMN "public"."t_report_file_info"."templte_id" IS '模板id';
COMMENT ON COLUMN "public"."t_report_file_info"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."t_report_file_info"."task_id" IS '任务id';
COMMENT ON COLUMN "public"."t_report_file_info"."mapping_name" IS '映射的名称（minio的存储路径）';
COMMENT ON COLUMN "public"."t_report_file_info"."create_time" IS '生成事件';
COMMENT ON COLUMN "public"."t_report_file_info"."status" IS '记录的状态';

-- ----------------------------
-- Primary Key structure for table t_report_file_info
-- ----------------------------
ALTER TABLE "public"."t_report_file_info" ADD CONSTRAINT "t_report_file_info_pkey" PRIMARY KEY ("id");