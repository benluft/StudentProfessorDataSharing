package backEnd;

import java.io.IOException;
import java.io.ObjectInputStream;

import sharedData.Assignment;
import sharedData.Email;
import sharedData.MessageNameConstants;
import sharedData.SocketMessage;
import sharedData.Submission;
import sharedData.User;

/**
 * Used when a student client wants to write to the database
 * 
 * @author Ben Luft and Rob Dunn
 *
 */
class StudentWriteMessage implements MessageNameConstants
{
	
	/**
	 * Decides what to write to which table 
	 * 
	 * @param message contains the data
	 * @param currentUser is the logged in user
	 * @param reader is the reader for the socket
	 */
	public StudentWriteMessage(SocketMessage message, User currentUser, ObjectInputStream reader)
	{
		if(message.getMessageType().equals(emailMessage))
		{
			Email mail = (Email) message;
			
			EmailWriter emailwriter = new EmailWriter(currentUser, mail);
		}
		
		else if(message.getMessageType().equals(submissionMessage))
		{
			Submission submission = (Submission) message;
			
			DBWriter writer = new DBWriter(submissionMessage.toLowerCase(), message);
			
			SocketMessage messageFile;
			
			if(submission.getID() == -1)
			{
				try 
				{
					messageFile = (SocketMessage) reader.readObject();
					PDFWriter pdfWriter = new PDFWriter(messageFile, currentUser.getType());
				} 
				catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
			System.out.println("Left if statement in StudentWriteMessage");
		}
		
		
		else if(message.getMessageType().equals(courseMessage))
		{
			System.out.println("Message to write is a course Message");
			DBWriter writer = new DBWriter(courseMessage.toLowerCase(), message);
		}
		
		else if(message.getMessageType().equals(enrolMessage))
		{
			DBWriter writer = new DBWriter(enrolMessage.toLowerCase(), message);
		}
		else if(message.getMessageType().equals(submissionMessage))
		{
			Submission submission = (Submission) message;
			
			DBWriter writer = new DBWriter(submissionMessage, message);
			
			SocketMessage messageFile;
			
			if(submission.getID() == -1)
			{
				try 
				{
					messageFile = (SocketMessage) reader.readObject();
					PDFWriter pdfWriter = new PDFWriter(messageFile, currentUser.getType());
				} 
				catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("Left StudentWriteMessage");
	}
}