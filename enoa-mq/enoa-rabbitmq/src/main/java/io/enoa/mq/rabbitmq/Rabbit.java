/*
 * Copyright (c) 2018, enoa (ein.windmill@outlook.com)
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
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.enoa.mq.rabbitmq;

import com.rabbitmq.client.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Rabbit {

  public static EPMRabbit epm() {
    return EPMRabbit.instance();
  }

  public static EnoaRabbit use(String name) {
    return epm().rabbit(name);
  }

  public static EnoaRabbit use() {
    return use("main");
  }

  public static Connection connection() {
    return getConnection();
  }

  public static Channel channel() {
    return use().channel();
  }

  public static int getChannelNumber() {
    return use().getChannelNumber();
  }

  public static Connection getConnection() {
    return use().getConnection();
  }

  public static void close() {
    use().close();
  }

  public static void close(int closeCode, String closeMessage) {
    use().close(closeCode, closeMessage);
  }

  public static void abort() {
    use().abort();
  }

  public static void abort(int closeCode, String closeMessage) {
    use().abort(closeCode, closeMessage);
  }

  public static void addReturnListener(ReturnListener listener) {
    use().addReturnListener(listener);
  }

  public static ReturnListener addReturnListener(ReturnCallback returnCallback) {
    return use().addReturnListener(returnCallback);
  }

  public static boolean removeReturnListener(ReturnListener listener) {
    return use().removeReturnListener(listener);
  }

  public static void clearReturnListeners() {
    use().clearReturnListeners();
  }

  public static void addConfirmListener(ConfirmListener listener) {
    use().addConfirmListener(listener);
  }

  public static ConfirmListener addConfirmListener(ConfirmCallback ackCallback, ConfirmCallback nackCallback) {
    return use().addConfirmListener(ackCallback, nackCallback);
  }

  public static boolean removeConfirmListener(ConfirmListener listener) {
    return use().removeConfirmListener(listener);
  }

  public static void clearConfirmListeners() {
    use().clearConfirmListeners();
  }

  public static Consumer getDefaultConsumer() {
    return use().getDefaultConsumer();
  }

  public static void setDefaultConsumer(Consumer consumer) {
    use().setDefaultConsumer(consumer);
  }

  public static void basicQos(int prefetchSize, int prefetchCount, boolean global) {
    use().basicQos(prefetchSize, prefetchCount, global);
  }

  public static void basicQos(int prefetchCount, boolean global) {
    use().basicQos(prefetchCount, global);
  }

  public static void basicQos(int prefetchCount) {
    use().basicQos(prefetchCount);
  }

  public static void basicPublish(String exchange, String routingKey, AMQP.BasicProperties props, byte[] body) {
    use().basicPublish(exchange, routingKey, props, body);
  }

  public static void basicPublish(String exchange, String routingKey, boolean mandatory, AMQP.BasicProperties props, byte[] body) {
    use().basicPublish(exchange, routingKey, mandatory, props, body);
  }

  public static void basicPublish(String exchange, String routingKey, boolean mandatory, boolean immediate, AMQP.BasicProperties props, byte[] body) {
    use().basicPublish(exchange, routingKey, mandatory, immediate, props, body);
  }

  public static AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, String type) {
    return use().exchangeDeclare(exchange, type);
  }

  public static AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type) {
    return use().exchangeDeclare(exchange, type);
  }

  public static AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, String type, boolean durable) {
    return use().exchangeDeclare(exchange, type, durable);
  }

  public static AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable) {
    return use().exchangeDeclare(exchange, type, durable);
  }

  public static AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, String type, boolean durable, boolean autoDelete, Map<String, Object> arguments) {
    return use().exchangeDeclare(exchange, type, durable, autoDelete, arguments);
  }

  public static AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, Map<String, Object> arguments) {
    return use().exchangeDeclare(exchange, type, durable, autoDelete, arguments);
  }

  public static AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, String type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments) {
    return use().exchangeDeclare(exchange, type, durable, autoDelete, internal, arguments);
  }

  public static AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments) {
    return use().exchangeDeclare(exchange, type, durable, autoDelete, internal, arguments);
  }

  public static void exchangeDeclareNoWait(String exchange, String type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments) {
    use().exchangeDeclare(exchange, type, durable, autoDelete, internal, arguments);
  }

  public static void exchangeDeclareNoWait(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments) {
    use().exchangeDeclareNoWait(exchange, type, durable, autoDelete, internal, arguments);
  }

  public static AMQP.Exchange.DeclareOk exchangeDeclarePassive(String name) {
    return use().exchangeDeclarePassive(name);
  }

  public static AMQP.Exchange.DeleteOk exchangeDelete(String exchange, boolean ifUnused) {
    return use().exchangeDelete(exchange, ifUnused);
  }

  public static void exchangeDeleteNoWait(String exchange, boolean ifUnused) {
    use().exchangeDeleteNoWait(exchange, ifUnused);
  }

  public static AMQP.Exchange.DeleteOk exchangeDelete(String exchange) {
    return use().exchangeDelete(exchange);
  }

  public static AMQP.Exchange.BindOk exchangeBind(String destination, String source, String routingKey) {
    return use().exchangeBind(destination, source, routingKey);
  }

  public static AMQP.Exchange.BindOk exchangeBind(String destination, String source, String routingKey, Map<String, Object> arguments) {
    return use().exchangeBind(destination, source, routingKey, arguments);
  }

  public static void exchangeBindNoWait(String destination, String source, String routingKey, Map<String, Object> arguments) {
    use().exchangeBindNoWait(destination, source, routingKey, arguments);
  }

  public static AMQP.Exchange.UnbindOk exchangeUnbind(String destination, String source, String routingKey) {
    return use().exchangeUnbind(destination, source, routingKey);
  }

  public static AMQP.Exchange.UnbindOk exchangeUnbind(String destination, String source, String routingKey, Map<String, Object> arguments) {
    return use().exchangeUnbind(destination, source, routingKey, arguments);
  }

  public static void exchangeUnbindNoWait(String destination, String source, String routingKey, Map<String, Object> arguments) {
    use().exchangeUnbindNoWait(destination, source, routingKey, arguments);
  }

  public static AMQP.Queue.DeclareOk queueDeclare() {
    return use().queueDeclare();
  }

  public static AMQP.Queue.DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments) {
    return use().queueDeclare(queue, durable, exclusive, autoDelete, arguments);
  }

  public static void queueDeclareNoWait(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments) {
    use().queueDeclareNoWait(queue, durable, exclusive, autoDelete, arguments);
  }

  public static AMQP.Queue.DeclareOk queueDeclarePassive(String queue) {
    return use().queueDeclarePassive(queue);
  }

  public static AMQP.Queue.DeleteOk queueDelete(String queue) {
    return use().queueDelete(queue);
  }

  public static AMQP.Queue.DeleteOk queueDelete(String queue, boolean ifUnused, boolean ifEmpty) {
    return use().queueDelete(queue, ifUnused, ifEmpty);
  }

  public static void queueDeleteNoWait(String queue, boolean ifUnused, boolean ifEmpty) {
    use().queueDeleteNoWait(queue, ifUnused, ifEmpty);
  }

  public static AMQP.Queue.BindOk queueBind(String queue, String exchange, String routingKey) {
    return use().queueBind(queue, exchange, routingKey);
  }

  public static AMQP.Queue.BindOk queueBind(String queue, String exchange, String routingKey, Map<String, Object> arguments) {
    return use().queueBind(queue, exchange, routingKey, arguments);
  }

  public static void queueBindNoWait(String queue, String exchange, String routingKey, Map<String, Object> arguments) {
    use().queueBindNoWait(queue, exchange, routingKey, arguments);
  }

  public static AMQP.Queue.UnbindOk queueUnbind(String queue, String exchange, String routingKey) {
    return use().queueUnbind(queue, exchange, routingKey);
  }

  public static AMQP.Queue.UnbindOk queueUnbind(String queue, String exchange, String routingKey, Map<String, Object> arguments) {
    return use().queueUnbind(queue, exchange, routingKey, arguments);
  }

  public static AMQP.Queue.PurgeOk queuePurge(String queue) {
    return use().queuePurge(queue);
  }

  public static GetResponse basicGet(String queue, boolean autoAck) {
    return use().basicGet(queue, autoAck);
  }

  public static void basicAck(long deliveryTag, boolean multiple) {
    use().basicAck(deliveryTag, multiple);
  }

  public static void basicNack(long deliveryTag, boolean multiple, boolean requeue) {
    use().basicNack(deliveryTag, multiple, requeue);
  }

  public static void basicReject(long deliveryTag, boolean requeue) {
    use().basicReject(deliveryTag, requeue);
  }

  public static String basicConsume(String queue, Consumer callback) {
    return use().basicConsume(queue, callback);
  }

  public static String basicConsume(String queue, DeliverCallback deliverCallback, CancelCallback cancelCallback) {
    return use().basicConsume(queue, deliverCallback, cancelCallback);
  }

  public static String basicConsume(String queue, DeliverCallback deliverCallback, ConsumerShutdownSignalCallback shutdownSignalCallback) {
    return use().basicConsume(queue, deliverCallback, shutdownSignalCallback);
  }

  public static String basicConsume(String queue, DeliverCallback deliverCallback, CancelCallback cancelCallback, ConsumerShutdownSignalCallback shutdownSignalCallback) {
    return use().basicConsume(queue, deliverCallback, cancelCallback, shutdownSignalCallback);
  }

  public static String basicConsume(String queue, boolean autoAck, Consumer callback) {
    return use().basicConsume(queue, autoAck, callback);
  }

  public static String basicConsume(String queue, boolean autoAck, DeliverCallback deliverCallback, CancelCallback cancelCallback) {
    return use().basicConsume(queue, autoAck, deliverCallback, cancelCallback);
  }

  public static String basicConsume(String queue, boolean autoAck, DeliverCallback deliverCallback, ConsumerShutdownSignalCallback shutdownSignalCallback) {
    return use().basicConsume(queue, autoAck, deliverCallback, shutdownSignalCallback);
  }

  public static String basicConsume(String queue, boolean autoAck, DeliverCallback deliverCallback, CancelCallback cancelCallback, ConsumerShutdownSignalCallback shutdownSignalCallback) {
    return use().basicConsume(queue, autoAck, deliverCallback, cancelCallback, shutdownSignalCallback);
  }

  public static String basicConsume(String queue, boolean autoAck, Map<String, Object> arguments, Consumer callback) {
    return use().basicConsume(queue, autoAck, arguments, callback);
  }

  public static String basicConsume(String queue, boolean autoAck, Map<String, Object> arguments, DeliverCallback deliverCallback, CancelCallback cancelCallback) {
    return use().basicConsume(queue, autoAck, arguments, deliverCallback, cancelCallback);
  }

  public static String basicConsume(String queue, boolean autoAck, Map<String, Object> arguments, DeliverCallback deliverCallback, ConsumerShutdownSignalCallback shutdownSignalCallback) {
    return use().basicConsume(queue, autoAck, arguments, deliverCallback, shutdownSignalCallback);
  }

  public static String basicConsume(String queue, boolean autoAck, Map<String, Object> arguments, DeliverCallback deliverCallback, CancelCallback cancelCallback, ConsumerShutdownSignalCallback shutdownSignalCallback) {
    return use().basicConsume(queue, autoAck, arguments, deliverCallback, cancelCallback, shutdownSignalCallback);
  }

  public static String basicConsume(String queue, boolean autoAck, String consumerTag, Consumer callback) {
    return use().basicConsume(queue, autoAck, consumerTag, callback);
  }

  public static String basicConsume(String queue, boolean autoAck, String consumerTag, DeliverCallback deliverCallback, CancelCallback cancelCallback) {
    return use().basicConsume(queue, autoAck, consumerTag, deliverCallback, cancelCallback);
  }

  public static String basicConsume(String queue, boolean autoAck, String consumerTag, DeliverCallback deliverCallback, ConsumerShutdownSignalCallback shutdownSignalCallback) {
    return use().basicConsume(queue, autoAck, consumerTag, deliverCallback, shutdownSignalCallback);
  }

  public static String basicConsume(String queue, boolean autoAck, String consumerTag, DeliverCallback deliverCallback, CancelCallback cancelCallback, ConsumerShutdownSignalCallback shutdownSignalCallback) {
    return use().basicConsume(queue, autoAck, consumerTag, deliverCallback, cancelCallback, shutdownSignalCallback);
  }

  public static String basicConsume(String queue, boolean autoAck, String consumerTag, boolean noLocal, boolean exclusive, Map<String, Object> arguments, Consumer callback) {
    return use().basicConsume(queue, autoAck, consumerTag, noLocal, exclusive, arguments, callback);
  }

  public static String basicConsume(String queue, boolean autoAck, String consumerTag, boolean noLocal, boolean exclusive, Map<String, Object> arguments, DeliverCallback deliverCallback, CancelCallback cancelCallback) {
    return use().basicConsume(queue, autoAck, consumerTag, noLocal, exclusive, arguments, deliverCallback, cancelCallback);
  }

  public static String basicConsume(String queue, boolean autoAck, String consumerTag, boolean noLocal, boolean exclusive, Map<String, Object> arguments, DeliverCallback deliverCallback, ConsumerShutdownSignalCallback shutdownSignalCallback) {
    return use().basicConsume(queue, autoAck, consumerTag, noLocal, exclusive, arguments, deliverCallback, shutdownSignalCallback);
  }

  public static String basicConsume(String queue, boolean autoAck, String consumerTag, boolean noLocal, boolean exclusive, Map<String, Object> arguments, DeliverCallback deliverCallback, CancelCallback cancelCallback, ConsumerShutdownSignalCallback shutdownSignalCallback) {
    return use().basicConsume(queue, autoAck, consumerTag, noLocal, exclusive, arguments, deliverCallback, cancelCallback, shutdownSignalCallback);
  }

  public static void basicCancel(String consumerTag) {
    use().basicCancel(consumerTag);
  }

  public static AMQP.Basic.RecoverOk basicRecover() {
    return use().basicRecover();
  }

  public static AMQP.Basic.RecoverOk basicRecover(boolean requeue) {
    return use().basicRecover();
  }

  public static AMQP.Tx.SelectOk txSelect() {
    return use().txSelect();
  }

  public static AMQP.Tx.CommitOk txCommit() {
    return use().txCommit();
  }

  public static AMQP.Tx.RollbackOk txRollback() {
    return use().txRollback();
  }

  public static AMQP.Confirm.SelectOk confirmSelect() {
    return use().confirmSelect();
  }

  public static long getNextPublishSeqNo() {
    return use().getNextPublishSeqNo();
  }

  public static boolean waitForConfirms() {
    return use().waitForConfirms();
  }

  public static boolean waitForConfirms(long timeout) {
    return use().waitForConfirms();
  }

  public static void waitForConfirmsOrDie() {
    use().waitForConfirmsOrDie();
  }

  public static void waitForConfirmsOrDie(long timeout) {
    use().waitForConfirmsOrDie(timeout);
  }

  public static void asyncRpc(Method method) {
    use().asyncRpc(method);
  }

  public static Command rpc(Method method) {
    return use().rpc(method);
  }

  public static long messageCount(String queue) {
    return use().messageCount(queue);
  }

  public static long consumerCount(String queue) {
    return use().consumerCount(queue);
  }

  public static CompletableFuture<Command> asyncCompletableRpc(Method method) {
    return use().asyncCompletableRpc(method);
  }

  public static void addShutdownListener(ShutdownListener listener) {
    use().addShutdownListener(listener);
  }

  public static void removeShutdownListener(ShutdownListener listener) {
    use().removeShutdownListener(listener);
  }

  public static ShutdownSignalException getCloseReason() {
    return use().getCloseReason();
  }

  public static void notifyListeners() {
    use().notifyListeners();
  }

  public static boolean isOpen() {
    return use().isOpen();
  }
}
