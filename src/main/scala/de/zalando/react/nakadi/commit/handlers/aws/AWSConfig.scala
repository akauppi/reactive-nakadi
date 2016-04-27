package de.zalando.react.nakadi.commit.handlers.aws

import com.typesafe.config._

case class AWSConfig(baseConfig: Config, commitConfig: Config, region: String, readCapacityUnits: Long, writeCapacityUnits: Long)

object AWSConfig {

  def apply(resource: Option[String] = None): AWSConfig = {
    val config = resource.fold(ConfigFactory.load())(ConfigFactory.load)
    new AWSConfig(
      baseConfig = config,
      commitConfig = config.getConfig("lease-management.commit.aws.dynamodb"),
      region = config.getConfig("lease-management.commit.aws").getString("region"),
      readCapacityUnits = config.getConfig("lease-management.commit.aws.dynamodb").getLong("read-capacity-units"),
      writeCapacityUnits = config.getConfig("lease-management.commit.aws.dynamodb").getLong("write-capacity-units")
    )
  }
}
