//16-1 Homework Part-1
//Zawaad M Shah
//zmshah16@ole.augie.edu
//BlackJack.java
import java.util.Vector;
import java.util.Iterator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class BlackJack extends JFrame implements ActionListener
{
      private DeckOfCards deck;
      private Vector<String> dealer;
      private Vector<String> player;
      private ImageIcon icoCard=new ImageIcon("cardImages/card.gif");
      private ImageIcon icon=null;
      private String filename=null;
      private JLabel[] lblPCard=new JLabel[7];
      private JLabel[] lblDCard=new JLabel[7];
      private JPanel pnlPlayerAndDealer=new JPanel(new GridLayout(2, 7));
      private JButton btnDeal=new JButton("Deal");
      private JButton btnPlayer=new JButton("Player");
      private JButton btnDealer=new JButton("Dealer");
      private JButton btnNew=new JButton("New"); 
      private JPanel pnlButton=new JPanel(new FlowLayout());
      public BlackJack()
      {
            addControls();
            registerListeners();
            setTitle("BlackJack");
            setSize(700, 350);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
      }
      public void addControls()
      {
            for(int i=0; i<7; ++i)
                lblDCard[i]=new JLabel("Dealer", JLabel.CENTER);
            for(int j=0; j<7; ++j)
                lblPCard[j]=new JLabel("Player", JLabel.CENTER);
            add(pnlPlayerAndDealer, BorderLayout.CENTER);
            add(pnlButton, BorderLayout.SOUTH);
            pnlPlayerAndDealerAddLabels();
            pnlAddButtons();
      }
      private void pnlPlayerAndDealerAddLabels()
      {
             for(int i=0; i<7; ++i)
                 pnlPlayerAndDealer.add(lblDCard[i]);
             for(int j=0; j<7; ++j)
                 pnlPlayerAndDealer.add(lblPCard[j]);
      }       
      private void pnlAddButtons()
      {
             pnlButton.add(btnDeal);
             pnlButton.add(btnPlayer);
             pnlButton.add(btnDealer);
             pnlButton.add(btnNew);
             btnPlayer.setEnabled(false);
             btnDealer.setEnabled(false);
             btnNew.setEnabled(false);
      }
      public void registerListeners()
      {
            btnDeal.addActionListener(this);
            btnPlayer.addActionListener(this);
            btnDealer.addActionListener(this);
            btnNew.addActionListener(this);
      }
      public void actionPerformed(ActionEvent e)
      {
            if(e.getSource()==btnDeal)
            {   
               btnPlayer.setEnabled(true);
               btnDeal.setEnabled(false);
               dealCards();
            }
            else if(e.getSource()==btnPlayer)
            {
            	    btnDealer.setEnabled(true);
                    btnPlayer.setEnabled(false);
                    playerTurn(); 
            }
            else if(e.getSource()==btnDealer)
            {
                    btnDealer.setEnabled(false);
                    btnNew.setEnabled(true);
                    dealerTurn();
                    whoWin(player, dealer);
            }
            else if(e.getSource()==btnNew)
            {
            	    btnDeal.setEnabled(true);
                    btnNew.setEnabled(false);
                    newGame();
            }
      }
      public void dealCards()
      {
    	  deck=new DeckOfCards();
          deck.shuffle();
          dealer=new Vector<String>();
          for(int i=0; i<2; ++i)
          {
              dealer.add(deck.deal());
              if(i==1)
              {   
                 displayDealer(i, true);
                 if(total(dealer)>21)
                 {
                    JOptionPane.showMessageDialog(null, "Dealer busted. You Win!");
                    enableNew();
                 }
              }  
              else
              {
                 displayDealer(i, false);
              }
          }      
          player=new Vector<String>();
          for(int j=0; j<2; ++j)
          {
              player.add(deck.deal());
              displayPlayer(j);
              if(total(player)>21)
              {  
                    JOptionPane.showMessageDialog(null, "You busted!"); 
                    enableNew();
              }
              else if(total(player)==21)
              {  
                      JOptionPane.showMessageDialog(null, "You Win!");
                      enableNew();
              }
           }
      }      
      private void playerTurn()
      {
            String choice;
            choice=JOptionPane.showInputDialog(null, "You have "+total(player)+" Hit or stay? H/S:");
            for(int j=2; choice.equals("H")||choice.equals("h"); ++j)
            {
               player.add(deck.deal());
               displayPlayer(j);
               if(total(player)>21)
               {   
                  JOptionPane.showMessageDialog(null, "You busted");
                  enableNew();
                  return;
               }
               else if(total(player)==21)
               {
                       JOptionPane.showMessageDialog(null, "You Win!");
                       enableNew();
                       return;
               }
               if(total(player)<21)
            	  choice=JOptionPane.showInputDialog(null, "You have "+total(player)+" Hit or stay? H/S:");
            }  
      }    
      private void dealerTurn()
      {     
             displayDealer(1, false);
             for(int i=2; total(dealer)<17; ++i)
             {
                 dealer.add(deck.deal());
                 displayDealer(i, false);
                 if(total(dealer)>21)
                 {
                    JOptionPane.showMessageDialog(null, "Dealer busted. You Win!");
                    enableNew();
                 }
                 else if(total(dealer)==21)
                 {
                    	 
                 }  	
              }       
      }
      private void displayPlayer(int j)
      {
             Iterator<String> iterPlayer=player.iterator();
             while(iterPlayer.hasNext())
             {
                   filename="cardImages/"+iterPlayer.next()+".gif";
                   icon=new ImageIcon(filename);
            	   lblPCard[j].setText("");
            	   lblPCard[j].setIcon(icon);
             }           
      }
      private void displayDealer(int i, boolean first)
      {      
             Iterator<String> iterDealer=dealer.iterator();
             if(first==true)
             {
            	lblDCard[i].setText("");
            	lblDCard[i].setIcon(icoCard);
             }
             else 
             {
                 while(iterDealer.hasNext())
                 {
                       filename="cardImages/"+iterDealer.next()+".gif";
                       icon=new ImageIcon(filename);
            	       lblDCard[i].setText("");
            	       lblDCard[i].setIcon(icon);
                 }
             }
      }
      private int total(Vector<String> v)
      {
             Iterator<String> iter=v.iterator();
             int sum=0;
             while(iter.hasNext())
             {
                   sum+=findRank(iter.next());
             }
             return sum;
      }
      public static int findRank(String card)
      {
            String s=card;
            if(s.startsWith("Two")) return 2;
            else if(s.startsWith("Three")) return 3;
            else if(s.startsWith("Four")) return 4;
            else if(s.startsWith("Five")) return 5;
            else if(s.startsWith("Six")) return 6;
            else if(s.startsWith("Seven")) return 7;
            else if(s.startsWith("Eight")) return 8;
            else if(s.startsWith("Nine")) return 9;
            else if(s.startsWith("Ten")) return 10;
            else if(s.startsWith("Ace")) return 11;
            else if(s.startsWith("Jack")) return 10;
            else if(s.startsWith("Queen")) return 10;             
            else return 10;
      }
      private void whoWin(Vector<String> player, Vector<String> dealer)
      { 
             if(total(player)>total(dealer) && total(dealer)<=21)
                JOptionPane.showMessageDialog(null, "You Win!");
             else if(total(player)<total(dealer) && total(dealer)<=21)
                     JOptionPane.showMessageDialog(null, "You loose");
             else if(total(player)==total(dealer) && total(dealer)<=21)
                 JOptionPane.showMessageDialog(null, "You tied");
      }
      private void newGame()
      {
    	  player = new Vector<>();
    	  dealer = new Vector<>();
    	  for(int i=0; i<7; ++i)
    	  {	  
    		  lblPCard[i].setIcon(null);
              lblDCard[i].setIcon(null);
              lblDCard[i].setText("Dealer");
              lblPCard[i].setText("Player"); 
    	  }
      } 
      private void enableNew()
      {
    	     btnDeal.setEnabled(false);
             btnPlayer.setEnabled(false);
             btnDealer.setEnabled(false);
             btnNew.setEnabled(true); 
      }
      public static void main(String[] args)
      {
            BlackJack f=new BlackJack();
      }
}