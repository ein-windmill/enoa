/*
 * Copyright (c) 2018, enoa (fewensa@enoa.io)
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

public class EnoaRabbit implements Channel {

  private Channel channel;

  public EnoaRabbit(Channel channel) {
    this.channel = channel;
  }

//  public Connection conn() {
//    return this.channel.getConnection();
//  }

  public Channel channel() {
    return this.channel;
  }


  @FunctionalInterface
  private interface _RunnerR<T> {
    T run() throws Exception;
  }

  @FunctionalInterface
  private interface _RunnerV {
    void run() throws Exception;
  }

  private <T> T runR(_RunnerR<T> runner) {
    try {
      return runner.run();
    } catch (Exception e) {
//      throw new EoException(e.getMessage(), e);
      throw new ERabbitMQException(e.getMessage(), e);
    }
  }

  private void runV(_RunnerV runner) {
    try {
      runner.run();
    } catch (Exception e) {
//      throw new EoException(e.getMessage(), e);
      throw new ERabbitMQException(e.getMessage(), e);
    }
  }

  @Override
  public int getChannelNumber() {
    return this.channel.getChannelNumber();
  }

  @Override
  public Connection getConnection() {
    return this.channel.getConnection();
  }

  @Override
  public void close() {
//    try {
//      this.channel.close();
//    } catch (Exception e) {
//      throw new EoException(EnoaTipKit.message("eo.tip.rabbitmq.close_fail"), e);
//    }
    this.runV(this.channel::close);
  }

  @Override
  public void close(int closeCode, String closeMessage) {
//    try {
//      this.channel.close(closeCode, closeMessage);
//    } catch (Exception e) {
//      throw new EoException(EnoaTipKit.message("eo.tip.rabbitmq.close_fail"), e);
//    }
    this.runV(() -> this.channel.close(closeCode, closeMessage));
  }

  @Override
  public void abort() {
    this.runV(this.channel::abort);
  }

  @Override
  public void abort(int closeCode, String closeMessage) {
    this.runV(() -> this.channel.abort(closeCode, closeMessage));
  }

  @Override
  public void addReturnListener(ReturnListener listener) {
    this.channel.addReturnListener(listener);
  }

  @Override
  public ReturnListener addReturnListener(ReturnCallback returnCallback) {
    return this.channel.addReturnListener(returnCallback);
  }

  @Override
  public boolean removeReturnListener(ReturnListener listener) {
    return this.channel.removeReturnListener(listener);
  }

  @Override
  public void clearReturnListeners() {
    this.channel.clearReturnListeners();
  }

  @Override
  public void addConfirmListener(ConfirmListener listener) {
    this.channel.addConfirmListener(listener);
  }

  @Override
  public ConfirmListener addConfirmListener(ConfirmCallback ackCallback, ConfirmCallback nackCallback) {
    return this.channel.addConfirmListener(ackCallback, nackCallback);
  }

  @Override
  public boolean removeConfirmListener(ConfirmListener listener) {
    return this.channel.removeConfirmListener(listener);
  }

  @Override
  public void clearConfirmListeners() {
    this.channel.clearConfirmListeners();
  }

  @Override
  public Consumer getDefaultConsumer() {
    return this.channel.getDefaultConsumer();
  }

  @Override
  public void setDefaultConsumer(Consumer consumer) {
    this.channel.setDefaultConsumer(consumer);
  }

  @Override
  public void basicQos(int prefetchSize, int prefetchCount, boolean global) {
    this.runV(() -> this.channel.basicQos(prefetchSize, prefetchCount, global));
  }

  @Override
  public void basicQos(int prefetchCount, boolean global) {
    this.runV(() -> this.channel.basicQos(prefetchCount, global));
  }

  @Override
  public void basicQos(int prefetchCount) {
    this.runV(() -> this.channel.basicQos(prefetchCount));
  }

  @Override
  public void basicPublish(String exchange, String routingKey, AMQP.BasicProperties props, byte[] body) {
    this.runV(() -> this.channel.basicPublish(exchange, routingKey, props, body));
  }

  @Override
  public void basicPublish(String exchange, String routingKey, boolean mandatory, AMQP.BasicProperties props, byte[] body) {
    this.runV(() -> this.channel.basicPublish(exchange, routingKey, mandatory, props, body));
  }

  @Override
  public void basicPublish(String exchange, String routingKey, boolean mandatory, boolean immediate, AMQP.BasicProperties props, byte[] body) {
    this.runV(() -> this.channel.basicPublish(exchange, routingKey, mandatory, immediate, props, body));
  }

  @Override
  public AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, String type) {
    return this.runR(() -> this.channel.exchangeDeclare(exchange, type));
  }

  @Override
  public AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type) {
    return this.runR(() -> this.channel.exchangeDeclare(exchange, type));
  }

  @Override
  public AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, String type, boolean durable) {
    return this.runR(() -> this.channel.exchangeDeclare(exchange, type, durable));
  }

  @Override
  public AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable) {
    return this.runR(() -> this.channel.exchangeDeclare(exchange, type, durable));
  }

  @Override
  public AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, String type, boolean durable, boolean autoDelete, Map<String, Object> arguments) {
    return this.runR(() -> this.channel.exchangeDeclare(exchange, type, durable, autoDelete, arguments));
  }

  @Override
  public AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, Map<String, Object> arguments) {
    return this.runR(() -> this.channel.exchangeDeclare(exchange, type, durable, autoDelete, arguments));
  }

  @Override
  public AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, String type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments) {
    return this.runR(() -> this.channel.exchangeDeclare(exchange, type, durable, autoDelete, internal, arguments));
  }

  @Override
  public AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments) {
    return this.runR(() -> this.channel.exchangeDeclare(exchange, type, durable, autoDelete, internal, arguments));
  }

  @Override
  public void exchangeDeclareNoWait(String exchange, String type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments) {
    this.runV(() -> this.channel.exchangeDeclareNoWait(exchange, type, durable, autoDelete, internal, arguments));
  }

  @Override
  public void exchangeDeclareNoWait(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments) {
    this.runV(() -> this.channel.exchangeDeclareNoWait(exchange, type, durable, autoDelete, internal, arguments));
  }

  @Override
  public AMQP.Exchange.DeclareOk exchangeDeclarePassive(String name) {
    return this.runR(() -> this.channel.exchangeDeclarePassive(name));
  }

  @Override
  public AMQP.Exchange.DeleteOk exchangeDelete(String exchange, boolean ifUnused) {
    return this.runR(() -> this.channel.exchangeDelete(exchange, ifUnused));
  }

  @Override
  public void exchangeDeleteNoWait(String exchange, boolean ifUnused) {
    this.runV(() -> this.channel.exchangeDeleteNoWait(exchange, ifUnused));
  }

  @Override
  public AMQP.Exchange.DeleteOk exchangeDelete(String exchange) {
    return this.runR(() -> this.channel.exchangeDelete(exchange));
  }

  @Override
  public AMQP.Exchange.BindOk exchangeBind(String destination, String source, String routingKey) {
    return this.runR(() -> this.channel.exchangeBind(destination, source, routingKey));
  }

  @Override
  public AMQP.Exchange.BindOk exchangeBind(String destination, String source, String routingKey, Map<String, Object> arguments) {
    return this.runR(() -> this.channel.exchangeBind(destination, source, routingKey, arguments));
  }

  @Override
  public void exchangeBindNoWait(String destination, String source, String routingKey, Map<String, Object> arguments) {
    this.runV(() -> this.channel.exchangeBindNoWait(destination, source, routingKey, arguments));
  }

  @Override
  public AMQP.Exchange.UnbindOk exchangeUnbind(String destination, String source, String routingKey) {
    return this.runR(() -> this.channel.exchangeUnbind(destination, source, routingKey));
  }

  @Override
  public AMQP.Exchange.UnbindOk exchangeUnbind(String destination, String source, String routingKey, Map<String, Object> arguments) {
    return this.runR(() -> this.channel.exchangeUnbind(destination, source, routingKey, arguments));
  }

  @Override
  public void exchangeUnbindNoWait(String destination, String source, String routingKey, Map<String, Object> arguments) {
    this.runV(() -> this.channel.exchangeUnbindNoWait(destination, source, routingKey, arguments));
  }

  @Override
  public AMQP.Queue.DeclareOk queueDeclare() {
    return this.runR(this.channel::queueDeclare);
  }

  @Override
  public AMQP.Queue.DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments) {
    return this.runR(() -> this.channel.queueDeclare(queue, durable, exclusive, autoDelete, arguments));
  }

  @Override
  public void queueDeclareNoWait(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments) {
    this.runV(() -> this.channel.queueDeclareNoWait(queue, durable, exclusive, autoDelete, arguments));
  }

  @Override
  public AMQP.Queue.DeclareOk queueDeclarePassive(String queue) {
    return this.runR(() -> this.channel.queueDeclarePassive(queue));
  }

  @Override
  public AMQP.Queue.DeleteOk queueDelete(String queue) {
    return this.runR(() -> this.channel.queueDelete(queue));
  }

  @Override
  public AMQP.Queue.DeleteOk queueDelete(String queue, boolean ifUnused, boolean ifEmpty) {
    return this.runR(() -> this.channel.queueDelete(queue, ifUnused, ifEmpty));
  }

  @Override
  public void queueDeleteNoWait(String queue, boolean ifUnused, boolean ifEmpty) {
    this.runV(() -> this.channel.queueDeleteNoWait(queue, ifUnused, ifEmpty));
  }

  @Override
  public AMQP.Queue.BindOk queueBind(String queue, String exchange, String routingKey) {
    return this.runR(() -> this.channel.queueBind(queue, exchange, routingKey));
  }

  @Override
  public AMQP.Queue.BindOk queueBind(String queue, String exchange, String routingKey, Map<String, Object> arguments) {
    return this.runR(() -> this.channel.queueBind(queue, exchange, routingKey, arguments));
  }

  @Override
  public void queueBindNoWait(String queue, String exchange, String routingKey, Map<String, Object> arguments) {
    this.runV(() -> this.channel.queueBindNoWait(queue, exchange, routingKey, arguments));
  }

  @Override
  public AMQP.Queue.UnbindOk queueUnbind(String queue, String exchange, String routingKey) {
    return this.runR(() -> this.channel.queueUnbind(queue, exchange, routingKey));
  }

  @Override
  public AMQP.Queue.UnbindOk queueUnbind(String queue, String exchange, String routingKey, Map<String, Object> arguments) {
    return this.runR(() -> this.channel.queueUnbind(queue, exchange, routingKey, arguments));
  }

  @Override
  public AMQP.Queue.PurgeOk queuePurge(String queue) {
    return this.runR(() -> this.channel.queuePurge(queue));
  }

  @Override
  public GetResponse basicGet(String queue, boolean autoAck) {
    return this.runR(() -> this.channel.basicGet(queue, autoAck));
  }

  @Override
  public void basicAck(long deliveryTag, boolean multiple) {
    this.runV(() -> this.channel.basicAck(deliveryTag, multiple));
  }

  @Override
  public void basicNack(long deliveryTag, boolean multiple, boolean requeue) {
    this.runV(() -> this.channel.basicNack(deliveryTag, multiple, requeue));
  }

  @Override
  public void basicReject(long deliveryTag, boolean requeue) {
    this.runV(() -> this.channel.basicReject(deliveryTag, requeue));
  }

  @Override
  public String basicConsume(String queue, Consumer callback) {
    return this.runR(() -> this.channel.basicConsume(queue, callback));
  }

  @Override
  public String basicConsume(String queue, DeliverCallback deliverCallback, CancelCallback cancelCallback) {
    return this.runR(() -> this.channel.basicConsume(queue, deliverCallback, cancelCallback));
  }

  @Override
  public String basicConsume(String queue, DeliverCallback deliverCallback, ConsumerShutdownSignalCallback shutdownSignalCallback) {
    return this.runR(() -> this.channel.basicConsume(queue, deliverCallback, shutdownSignalCallback));
  }

  @Override
  public String basicConsume(String queue, DeliverCallback deliverCallback, CancelCallback cancelCallback, ConsumerShutdownSignalCallback shutdownSignalCallback) {
    return this.runR(() -> this.channel.basicConsume(queue, deliverCallback, cancelCallback, shutdownSignalCallback));
  }

  @Override
  public String basicConsume(String queue, boolean autoAck, Consumer callback) {
    return this.runR(() -> this.channel.basicConsume(queue, autoAck, callback));
  }

  @Override
  public String basicConsume(String queue, boolean autoAck, DeliverCallback deliverCallback, CancelCallback cancelCallback) {
    return this.runR(() -> this.channel.basicConsume(queue, autoAck, deliverCallback, cancelCallback));
  }

  @Override
  public String basicConsume(String queue, boolean autoAck, DeliverCallback deliverCallback, ConsumerShutdownSignalCallback shutdownSignalCallback) {
    return this.runR(() -> this.channel.basicConsume(queue, autoAck, deliverCallback, shutdownSignalCallback));
  }

  @Override
  public String basicConsume(String queue, boolean autoAck, DeliverCallback deliverCallback, CancelCallback cancelCallback, ConsumerShutdownSignalCallback shutdownSignalCallback) {
    return this.runR(() -> this.channel.basicConsume(queue, autoAck, deliverCallback, cancelCallback, shutdownSignalCallback));
  }

  @Override
  public String basicConsume(String queue, boolean autoAck, Map<String, Object> arguments, Consumer callback) {
    return this.runR(() -> this.channel.basicConsume(queue, autoAck, arguments, callback));
  }

  @Override
  public String basicConsume(String queue, boolean autoAck, Map<String, Object> arguments, DeliverCallback deliverCallback, CancelCallback cancelCallback) {
    return this.runR(() -> this.channel.basicConsume(queue, autoAck, arguments, deliverCallback, cancelCallback));
  }

  @Override
  public String basicConsume(String queue, boolean autoAck, Map<String, Object> arguments, DeliverCallback deliverCallback, ConsumerShutdownSignalCallback shutdownSignalCallback) {
    return this.runR(() -> this.channel.basicConsume(queue, autoAck, arguments, deliverCallback, shutdownSignalCallback));
  }

  @Override
  public String basicConsume(String queue, boolean autoAck, Map<String, Object> arguments, DeliverCallback deliverCallback, CancelCallback cancelCallback, ConsumerShutdownSignalCallback shutdownSignalCallback) {
    return this.runR(() -> this.channel.basicConsume(queue, autoAck, arguments, deliverCallback, cancelCallback, shutdownSignalCallback));
  }

  @Override
  public String basicConsume(String queue, boolean autoAck, String consumerTag, Consumer callback) {
    return this.runR(() -> this.channel.basicConsume(queue, autoAck, consumerTag, callback));
  }

  @Override
  public String basicConsume(String queue, boolean autoAck, String consumerTag, DeliverCallback deliverCallback, CancelCallback cancelCallback) {
    return this.runR(() -> this.channel.basicConsume(queue, autoAck, consumerTag, deliverCallback, cancelCallback));
  }

  @Override
  public String basicConsume(String queue, boolean autoAck, String consumerTag, DeliverCallback deliverCallback, ConsumerShutdownSignalCallback shutdownSignalCallback) {
    return this.runR(() -> this.channel.basicConsume(queue, autoAck, consumerTag, deliverCallback, shutdownSignalCallback));
  }

  @Override
  public String basicConsume(String queue, boolean autoAck, String consumerTag, DeliverCallback deliverCallback, CancelCallback cancelCallback, ConsumerShutdownSignalCallback shutdownSignalCallback) {
    return this.runR(() -> this.channel.basicConsume(queue, autoAck, consumerTag, deliverCallback, cancelCallback, shutdownSignalCallback));
  }

  @Override
  public String basicConsume(String queue, boolean autoAck, String consumerTag, boolean noLocal, boolean exclusive, Map<String, Object> arguments, Consumer callback) {
    return this.runR(() -> this.channel.basicConsume(queue, autoAck, consumerTag, noLocal, exclusive, arguments, callback));
  }

  @Override
  public String basicConsume(String queue, boolean autoAck, String consumerTag, boolean noLocal, boolean exclusive, Map<String, Object> arguments, DeliverCallback deliverCallback, CancelCallback cancelCallback) {
    return this.runR(() -> this.channel.basicConsume(queue, autoAck, consumerTag, noLocal, exclusive, arguments, deliverCallback, cancelCallback));
  }

  @Override
  public String basicConsume(String queue, boolean autoAck, String consumerTag, boolean noLocal, boolean exclusive, Map<String, Object> arguments, DeliverCallback deliverCallback, ConsumerShutdownSignalCallback shutdownSignalCallback) {
    return this.runR(() -> this.channel.basicConsume(queue, autoAck, consumerTag, noLocal, exclusive, arguments, deliverCallback, shutdownSignalCallback));
  }

  @Override
  public String basicConsume(String queue, boolean autoAck, String consumerTag, boolean noLocal, boolean exclusive, Map<String, Object> arguments, DeliverCallback deliverCallback, CancelCallback cancelCallback, ConsumerShutdownSignalCallback shutdownSignalCallback) {
    return this.runR(() -> this.channel.basicConsume(queue, autoAck, consumerTag, noLocal, exclusive, arguments, deliverCallback, cancelCallback, shutdownSignalCallback));
  }

  @Override
  public void basicCancel(String consumerTag) {
    this.runV(() -> this.channel.basicCancel(consumerTag));
  }

  @Override
  public AMQP.Basic.RecoverOk basicRecover() {
    return this.runR(this.channel::basicRecover);
  }

  @Override
  public AMQP.Basic.RecoverOk basicRecover(boolean requeue) {
    return this.runR(() -> this.channel.basicRecover(requeue));
  }

  @Override
  public AMQP.Tx.SelectOk txSelect() {
    return this.runR(this.channel::txSelect);
  }

  @Override
  public AMQP.Tx.CommitOk txCommit() {
    return this.runR(this.channel::txCommit);
  }

  @Override
  public AMQP.Tx.RollbackOk txRollback() {
    return this.runR(this.channel::txRollback);
  }

  @Override
  public AMQP.Confirm.SelectOk confirmSelect() {
    return this.runR(this.channel::confirmSelect);
  }

  @Override
  public long getNextPublishSeqNo() {
    return this.channel.getNextPublishSeqNo();
  }

  @Override
  public boolean waitForConfirms() {
    return this.runR(this.channel::waitForConfirms);
  }

  @Override
  public boolean waitForConfirms(long timeout) {
    return this.runR(() -> this.channel.waitForConfirms(timeout));
  }

  @Override
  public void waitForConfirmsOrDie() {
    this.runV(this.channel::waitForConfirmsOrDie);
  }

  @Override
  public void waitForConfirmsOrDie(long timeout) {
    this.runV(() -> this.channel.waitForConfirmsOrDie(timeout));
  }

  @Override
  public void asyncRpc(Method method) {
    this.runV(() -> this.channel.asyncRpc(method));
  }

  @Override
  public Command rpc(Method method) {
    return this.runR(() -> this.channel.rpc(method));
  }

  @Override
  public long messageCount(String queue) {
    return this.runR(() -> this.channel.messageCount(queue));
  }

  @Override
  public long consumerCount(String queue) {
    return this.runR(() -> this.channel.consumerCount(queue));
  }

  @Override
  public CompletableFuture<Command> asyncCompletableRpc(Method method) {
    return this.runR(() -> this.channel.asyncCompletableRpc(method));
  }

  @Override
  public void addShutdownListener(ShutdownListener listener) {
    this.channel.addShutdownListener(listener);
  }

  @Override
  public void removeShutdownListener(ShutdownListener listener) {
    this.channel.removeShutdownListener(listener);
  }

  @Override
  public ShutdownSignalException getCloseReason() {
    return this.channel.getCloseReason();
  }

  @Override
  public void notifyListeners() {
    this.channel.notifyListeners();
  }

  @Override
  public boolean isOpen() {
    return this.channel.isOpen();
  }


}
