package de.zalando.react.nakadi.properties

import de.zalando.react.nakadi.NakadiMessages._
import de.zalando.react.nakadi.commit.handlers.BaseLeaseManager

import scala.concurrent.duration._
import scala.language.postfixOps


object ConsumerProperties {

  def apply(serverProperties: ServerProperties,
            tokenProvider: Option[() => String],
            topic: String,
            groupId: String,
            partition: String,
            commitHandler: BaseLeaseManager): ConsumerProperties = {
    new ConsumerProperties(
      serverProperties = serverProperties,
      tokenProvider = tokenProvider,
      topic = topic,
      groupId = groupId,
      partition = partition,
      commitHandler = commitHandler
    )
  }
}

case class ConsumerProperties(
  serverProperties: ServerProperties,
  tokenProvider: Option[() => String],
  topic: String,
  groupId: String,
  partition: String,
  commitHandler: BaseLeaseManager,
  offset: Option[Offset] = None,
  commitInterval: FiniteDuration = 30.seconds,
  batchLimit: Int = 0,
  batchFlushTimeoutInSeconds: FiniteDuration = 30.seconds,
  streamLimit: Int = 0,
  streamTimeoutInSeconds: FiniteDuration = 0.seconds,
  streamKeepAliveLimit: Int = 0,
  pollParallelism: Int = 0,
  staleLeaseDelta: FiniteDuration = 300.seconds,
  leaseHolder: String = "test-lease-holder" // FIXME - set this to make sense
) {

  /**
    * Use custom interval for auto-commit or commit flushing on manual commit.
    */
  def commitInterval(time: FiniteDuration): ConsumerProperties =
    this.copy(commitInterval = time)

  def readFromStartOfStream(): ConsumerProperties =
    this.copy(offset = Some(BeginOffset))

}
