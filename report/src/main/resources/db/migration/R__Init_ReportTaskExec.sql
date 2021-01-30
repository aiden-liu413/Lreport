/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : PostgreSQL
 Source Server Version : 90400
 Source Host           : localhost:5432
 Source Catalog        : report
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90400
 File Encoding         : 65001

 Date: 30/01/2021 18:40:43
*/


-- ----------------------------
-- Table structure for t_report_task_exec_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_report_task_exec_info";
CREATE TABLE "public"."t_report_task_exec_info"
(
    "id"          varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "exec_status" varchar(255) COLLATE "pg_catalog"."default",
    "content"     text COLLATE "pg_catalog"."default",
    "exec_time"   timestamp(6),
    "spent_time"  varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."t_report_task_exec_info"."exec_status" IS '执行状态';
COMMENT
ON COLUMN "public"."t_report_task_exec_info"."content" IS '日志详情';
COMMENT
ON COLUMN "public"."t_report_task_exec_info"."exec_time" IS '执行时间';
COMMENT
ON COLUMN "public"."t_report_task_exec_info"."spent_time" IS '任务耗时';

-- ----------------------------
-- Primary Key structure for table t_report_task_exec_info
-- ----------------------------
ALTER TABLE "public"."t_report_task_exec_info"
    ADD CONSTRAINT "t_report_task_exec_info_pkey1" PRIMARY KEY ("id");
