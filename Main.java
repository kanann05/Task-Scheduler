import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.*;
import java.util.*;




public class Main  {
    static class RoundedButton extends JButton {
        private Color backgroundColor;
        private Color foregroundColor;
        private int cornerRadius;


        RoundedButton(String text) {
            super(text);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setForeground(Color.WHITE);
            setBackground(Color.BLUE);
            setFont(new Font("Arial", Font.PLAIN, 14));
            this.cornerRadius = 10; // Default corner radius
        }


        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            g2d.setColor(getForeground());
            super.paintComponent(g2d);
            g2d.dispose();
        }


        public void setCornerRadius(int cornerRadius) {
            this.cornerRadius = cornerRadius;
            repaint();
        }
    }
    static class RoundedTextField extends JTextField {
        private int cornerRadius;


        public RoundedTextField() {
            super();
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Add padding
            setFont(new Font("Arial", Font.PLAIN, 14));
            this.cornerRadius = 10; // Default corner radius
        }


        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
            super.paintComponent(g2d);
            g2d.dispose();
        }


        @Override
        protected void paintBorder(Graphics g) {
            // Do not paint the border
        }


        public void setCornerRadius(int cornerRadius) {
            this.cornerRadius = cornerRadius;
            repaint();
        }
    }
    public static PriorityQueue<task> pq;
    public static void createTTpq(PriorityQueue<task> pq) {

        boolean hasvoid = false;
        int voidtime = 000;
        int voidstart = 000;
        while (!pq.isEmpty()) {
            task currTask = pq.poll();
            if (currTask.time <= voidtime && hasvoid) {
                // Schedule the task in void time
                currTask.start = starttime;
                currTask.end = breaktime;
                tt.add(currTask);
                hasvoid = false;
                continue;
            }
            if (currtime + currTask.time <= breaktime || currtime >= breakend) {
                currTask.start = currtime;
                currTask.end = currtime + currTask.time;
                currtime = currTask.end;
                tt.add(currTask);
                continue;
            }
            if((currtime + currTask.time > breaktime && currtime <= breaktime) && !hasvoid) {
                voidstart = currtime;
                voidtime = breaktime - currtime;
                hasvoid = true;
                currtime = breakend;
                currTask.start = currtime;
                currTask.end = currtime += currTask.time;
                currtime = currTask.end;
                tt.add(currTask);
            }
        }
    }
    public static ArrayList<task> tt = new ArrayList<>();
    public static int starttime;
    public static int breaktime;
    public static int breakend;
    public static int endtime;
    public static int currtime;
    public static int ptr;
    static class task {
        int pri;
        int deadl = 2400;
        int time;
        int start, end;
        String details;
        task(String d, int p, int de, int t) {
            this.pri = p;
            this.deadl = de;
            this.details = d;
            this.time = t;
        }
        task(int x, int y) {
            this.start = x;
            this.end = y;
        }
    }
    public static ArrayList<task> task_list = new ArrayList<>();




    public static void main(String[] args) {

        JFrame f = new JFrame("Time Table creator");
        f.setSize(900, 380);
        f.setLayout(null);




        JLabel l1 = new JLabel("Commencing time (military format)");
        l1.setBounds(30, 10, 300, 50); // Adjust the width to fit the entire label text
        l1.setFont(new Font("Cambria", Font.PLAIN, 16));
        f.add(l1);




        JTextField t1 = new JTextField();
        t1.setBounds(350, 20, 100, 30);
        f.add(t1);




        JLabel l2 = new JLabel("Ending time (military format)");
        l2.setBounds(30, 70, 300, 50); // Adjust the Y-coordinate
        l2.setFont(new Font("Cambria", Font.PLAIN, 16));
        f.add(l2);




        JTextField t2 = new JTextField();
        t2.setBounds(350, 80, 100, 30);
        f.add(t2);




        JLabel l3 = new JLabel("Break time available?");
        l3.setBounds(30, 130, 300, 50); // Adjust the Y-coordinate
        l3.setFont(new Font("Cambria", Font.PLAIN, 16));
        f.add(l3);




        JLabel l4 = new JLabel("Break commencing time");
        l4.setBounds(30, 190, 300, 50); // Adjust the Y-coordinate
        l4.setFont(new Font("Cambria", Font.PLAIN, 16));
        l4.setVisible(false);
        f.add(l4);




        JTextField t4 = new JTextField();
        t4.setBounds(350, 200, 100, 30);
        t4.setVisible(false);
        f.add(t4);




        JLabel l5 = new JLabel("Break ending time");
        l5.setBounds(30, 250, 300, 50); // Adjust the Y-coordinate
        l5.setFont(new Font("Cambria", Font.PLAIN, 16));
        l5.setVisible(false);
        f.add(l5);




        JTextField t5 = new JTextField();
        t5.setBounds(350, 260, 100, 30);
        t5.setVisible(false);
        f.add(t5);




        JCheckBox cb = new JCheckBox();
        cb.setBounds(350, 140, 40, 30);
        cb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    l4.setVisible(true);
                    l5.setVisible(true);
                    t4.setVisible(true);
                    t5.setVisible(true);
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    l4.setVisible(false);
                    l5.setVisible(false);
                    t4.setVisible(false);
                    t5.setVisible(false);
                }
            }
        });
        f.add(cb);




        JLabel l6 = new JLabel("Task details");
        l6.setBounds(530, 75, 200, 40);
        f.add(l6);
        JTextField t6 = new JTextField();
        t6.setBounds(620, 75, 180, 35);
        f.add(t6);




        JLabel l7 = new JLabel("Priority");
        l7.setBounds(530, 125, 200, 40);
        f.add(l7);
        JTextField t7 = new JTextField();
        t7.setBounds(620, 125, 180, 35);
        f.add(t7);




        JLabel l8 = new JLabel("Deadline?");
        l8.setBounds(530, 175, 200, 40);
        f.add(l8);
        JTextField t8 = new JTextField();
        t8.setBounds(690, 175, 110, 35);
        t8.setVisible(false);
        f.add(t8);
        JCheckBox cbd = new JCheckBox();
        cbd.setBounds(620, 175, 50, 50);
        cbd.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    t8.setVisible(true);
                }
                if(e.getStateChange() == ItemEvent.DESELECTED) {
                    t8.setVisible(false);
                }
            }
        });
        f.add(cbd);




        JLabel l9 = new JLabel("Time required");
        l9.setBounds(530, 225, 200, 40);
        f.add(l9);
        JTextField t9 = new JTextField();
        t9.setBounds(620, 225, 180, 35);
        t9.setVisible(true);
        f.add(t9);




        RoundedButton b1 = new RoundedButton("Add task");
        b1.setCornerRadius(10);
        b1.setFont(new Font("Arial", Font.PLAIN, 12));
        b1.setBackground(Color.DARK_GRAY);
        b1.setBounds(600, 30, 150, 30);


        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int deadLine = 2400;
                if(cbd.isSelected()) {
                    deadLine = Integer.parseInt(t8.getText());
                }
                task newTask = new task(t6.getText(), Integer.parseInt(t7.getText()), deadLine, Integer.parseInt(t9.getText()));
                task_list.add(newTask);
                t6.setText("");
                t7.setText("");
                t8.setText("");
                t9.setText("");
                System.out.println(task_list.size());



            }
        });
        b1.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {


            }


            @Override
            public void mousePressed(MouseEvent e) {
                b1.setBackground(new Color(100, 100, 100));
            }


            @Override
            public void mouseReleased(MouseEvent e) {
                b1.setBackground(Color.DARK_GRAY);
            }


            @Override
            public void mouseEntered(MouseEvent e) {
                b1.setBackground(new Color(50, 50, 50));
            }


            @Override
            public void mouseExited(MouseEvent e) {
                b1.setBackground(Color.DARK_GRAY);
            }
        });
        f.add(b1);





        Collections.sort(tt, (tx, ty) -> {
            return tx.start - ty.start;
        });

        RoundedButton  b2 = new RoundedButton("Get schedule");
        b2.setCornerRadius(10);
        b2.setFont(new Font("Arial", Font.PLAIN, 12));
        b2.setBackground(Color.DARK_GRAY);
        b2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {


            }


            @Override
            public void mousePressed(MouseEvent e) {
                b2.setBackground(new Color(100, 100, 100));
            }


            @Override
            public void mouseReleased(MouseEvent e) {
                b2.setBackground(Color.DARK_GRAY);
            }


            @Override
            public void mouseEntered(MouseEvent e) {
                b2.setBackground(new Color(50, 50, 50));
            }


            @Override
            public void mouseExited(MouseEvent e) {
                b2.setBackground(Color.DARK_GRAY);
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (task_list.isEmpty()) {
                    pq = new PriorityQueue<>();
                } else {
                    pq = new PriorityQueue<>(task_list.size(), new Comparator<task>() {
                        public int compare(task t1, task t2) {
                            if (t1.deadl == t2.deadl) return t1.pri - t2.pri;
                            else return t1.deadl - t2.deadl;
                        }
                    });
                }
                for(int i = 0; i<task_list.size(); i++) {
                    pq.add(task_list.get(i));
                }
                ptr = 0;
                // Update starttime based on the text entered in t1
                starttime = Integer.parseInt(t1.getText()); // Assuming the input is valid
                endtime = Integer.parseInt(t2.getText());
                currtime = starttime;
                if(cb.isSelected()) {
                    breaktime = Integer.parseInt(t4.getText());
                    breakend = Integer.parseInt(t5.getText());
                } else {
                    breaktime = 2400;
                    endtime = 2400;
                }
                createTTpq(pq);
                f.setVisible(false);

                JFrame framenxt = new JFrame("Generated time table");
                framenxt.setSize(900, 380);
                framenxt.setLayout(null);
                framenxt.setVisible(true);

                JLabel nl1 = new JLabel("Task 1");
                nl1.setFont(new Font("Ariel", Font.PLAIN, 15));
                nl1.setSize(100, 50);
                nl1.setBounds(400, 30, 100, 50);
                framenxt.add(nl1);

                System.out.println(tt.get(0).details);
                System.out.println(tt.get(0).start);
                System.out.println(tt.get(0).end);
                JLabel nl2 = new JLabel(tt.get(0).details);
                nl2.setFont(new Font("Ariel", Font.ITALIC, 15));
                nl2.setSize(100, 50);
                nl2.setBounds(400, 50, 100, 50);
                framenxt.add(nl2);

                JLabel nl3 = new JLabel("Start time : ");
                nl3.setFont(new Font("Ariel", Font.PLAIN, 15));
                nl3.setSize(100, 50);
                nl3.setBounds(400, 90, 100, 50);
                framenxt.add(nl3);

                JLabel nl4 = new JLabel(Integer.toString(tt.get(0).start));
                nl4.setFont(new Font("Ariel", Font.ITALIC, 15));
                nl4.setSize(100, 50);
                nl4.setBounds(400, 110, 100, 50);
                framenxt.add(nl4);

                JLabel nl5 = new JLabel("End time : ");
                nl5.setFont(new Font("Ariel", Font.PLAIN, 15));
                nl5.setSize(100, 50);
                nl5.setBounds(400, 150, 100, 50);
                framenxt.add(nl5);

                JLabel nl6 = new JLabel(Integer.toString(tt.get(0).end) );
                nl6.setFont(new Font("Ariel", Font.ITALIC, 15));
                nl6.setSize(100, 50);
                nl6.setBounds(400, 170, 100, 50);
                framenxt.add(nl6);

                RoundedButton  nxt = new RoundedButton("Next");
                b2.setCornerRadius(10);
                b2.setFont(new Font("Arial", Font.PLAIN, 16));
                b2.setBackground(Color.DARK_GRAY);
                nxt.setBounds(350, 265, 150, 30);
                nxt.setBackground(Color.DARK_GRAY);

                nxt.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {


                    }


                    @Override
                    public void mousePressed(MouseEvent e) {
                        nxt.setBackground(new Color(100, 100, 100));
                    }


                    @Override
                    public void mouseReleased(MouseEvent e) {
                        nxt.setBackground(Color.DARK_GRAY);
                    }


                    @Override
                    public void mouseEntered(MouseEvent e) {
                        nxt.setBackground(new Color(50, 50, 50));
                    }


                    @Override
                    public void mouseExited(MouseEvent e) {
                        nxt.setBackground(Color.DARK_GRAY);
                    }
                });
                ptr++;
                JLabel theend = new JLabel();
                theend.setText("End of the table");
                theend.setFont(new Font("Ariel", Font.PLAIN, 17));
                theend.setBounds(370, 100, 200, 40);
                theend.setVisible(false);
                framenxt.add(theend);
                nxt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(ptr >= tt.size()) {
                            nl1.setVisible(false);
                            nl2.setVisible(false);
                            nl3.setVisible(false);
                            nl4.setVisible(false);
                            nl5.setVisible(false);
                            nl6.setVisible(false);
                            theend.setVisible(true);
                        }
                        else {
                            task curr = tt.get(ptr);
                            nl1.setText("Task " + (ptr+1));
                            nl2.setText(curr.details);
                            nl4.setText(Integer.toString(curr.start));
                            nl6.setText(Integer.toString(curr.end));
                            ptr++;
                        }
                    }
                });
                framenxt.add(nxt);
            }
        });
        b2.setBounds(600, 280, 150, 30);
        f.add(b2);
        f.setVisible(true);
    }
}
