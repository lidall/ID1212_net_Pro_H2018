/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

/**
 *
 * @author Lida
 */
import java.io.IOException;
import org.apache.commons.lang.SerializationUtils;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.ShutdownSignalException;
import model.DataInfo;
import model.DatabaseFunction;
import static startup.SystemServer.getCTX;
import model.UserInfo;
import com.rabbitmq.client.ConsumerCancelledException;

public class Receiver extends BaseConnector implements Runnable, Consumer {
    
    boolean isBroken = false;

    public Receiver(String queueName) throws Exception {
        super(queueName);
    }

     public void run() {
     errorHandeling();
    }

    protected void errorHandeling() {
		
        try {
            if (isBroken) {

                    System.out.println(" [x] Something worng ----"+ channel.isOpen());

            }
                channel.basicConsume(queueName, true, this);
        } catch (IOException e) {

            e.printStackTrace();

        } catch (ShutdownSignalException e) {
            try {

                    if (channel.isOpen()) {

                            channel.basicConsume(queueName, true,this);
                            isBroken = false;
                            System.out.println(" [x] Recover sucess ----");
                    }
            } catch (Exception e1) {

                    isBroken = true;
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    System.out.println(" [x] Thread worng"
                            + "--- ShutdownSignalException-Exception");
            }
        } catch (ConsumerCancelledException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println(" [x] Thread exit"
                        + "----ConsumerCancelledException");
        } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println(" [x] Exit ----Exception");

        }

    }
    

    public void handleConsumeOk(String consumerTag) {
        System.out.println("Consumer "+consumerTag +" registered");
    }

    public void handleDelivery(String consumerTag, Envelope env,
                BasicProperties props, byte[] body) throws IOException {
        DataInfo dataInfo = (DataInfo)SerializationUtils.deserialize(body);
        System.out.println("Message ( "
                + "Name : " + dataInfo.getItemName() 
                + " , ID : " + dataInfo.getItemID() 
                + " ) received.");
        

        String dataTemp = "("+ dataInfo.getItemName()+")";
        String transferName = getCTX(dataTemp,"(","-");
        String transferID = getCTX(dataTemp,"-",")");
        DatabaseFunction.uploadDataInsert(new UserInfo(transferName,transferID),dataInfo.getItemID());
        
    }

    public void handleCancelOk(String consumerTag) {
    }
    public void handleCancel(String consumerTag) throws IOException {
    }
    public void handleShutdownSignal(String consumerTag,
            ShutdownSignalException sig) {
    }
    public void handleRecoverOk(String consumerTag) {
    }
}