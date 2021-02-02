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

 Date: 30/01/2021 18:40:55
*/


-- ----------------------------
-- Table structure for t_report_task_exec_r_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_report_task_exec_r_info";
CREATE TABLE "public"."t_report_task_exec_r_info"
(
    "id"         varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "task_id"    varchar(32) COLLATE "pg_catalog"."default",
    "templte_id" varchar(32) COLLATE "pg_catalog"."default",
    "exec_id"    varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."t_report_task_exec_r_info"."task_id" IS '任务id';
COMMENT
ON COLUMN "public"."t_report_task_exec_r_info"."templte_id" IS '模板id';
COMMENT
ON COLUMN "public"."t_report_task_exec_r_info"."exec_id" IS '执行记录id';

-- ----------------------------
-- Primary Key structure for table t_report_task_exec_r_info
-- ----------------------------
ALTER TABLE "public"."t_report_task_exec_r_info"
    ADD CONSTRAINT "t_report_task_exec_info_pkey" PRIMARY KEY ("id");
