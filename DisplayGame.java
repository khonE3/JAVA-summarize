import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class DisplayGame extends JPanel implements ActionListener {
    private Timer timer;
    private int score = 0;
    private boolean gameOver = false;
    private int tick = 0; // For animations

    // Game Speed
    private int baseSpeed = 10;
    private int currentSpeed = 10;

    // Player properties (Fat Cat)
    private int playerWidth = 60;
    private int playerHeight = 40;
    private int defaultPlayerY = 260;
    private int playerX = 50;
    private int playerY = 260;
    
    private int playerVelocityY = 0;
    private int gravity = 2;
    private int jumpStrength = -20;
    private boolean isJumping = false;
    private boolean isSliding = false;

    // Obstacles and Particles
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Particle> particles;
    private Random random;

    // Scrolling Offsets
    private int groundOffset = 0;
    private int cloudOffset = 0;
    private int mountainOffset = 0;

    // Effects
    private int flashAlpha = 0;

    public DisplayGame() {
        setPreferredSize(new Dimension(800, 400));
        setFocusable(true);

        obstacles = new ArrayList<>();
        particles = new ArrayList<>();
        random = new Random();

        // Key bindings for Jump and Slide
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
                    if (!isJumping && !isSliding && !gameOver) {
                        playerVelocityY = jumpStrength;
                        isJumping = true;
                        
                        // Jump Effect: Dust Particles
                        for(int i=0; i<5; i++) {
                            particles.add(new Particle(playerX + 10 + random.nextInt(40), playerY + playerHeight, 
                                random.nextInt(10)+5, -random.nextInt(3), -random.nextInt(3)-1, 15, Color.LIGHT_GRAY));
                        }
                    }
                    if (gameOver) {
                        restartGame();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (!isJumping && !gameOver) {
                        if (!isSliding) {
                            // Slide Effect: Dust Particles behind
                            for(int i=0; i<5; i++) {
                                particles.add(new Particle(playerX, playerY + playerHeight, 
                                    random.nextInt(8)+4, -random.nextInt(5)-2, -random.nextInt(2), 10, Color.WHITE));
                            }
                        }
                        isSliding = true;
                        playerHeight = 25; // Duck down
                        playerWidth = 75; // Stretch out
                        playerY = defaultPlayerY + 15; 
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    isSliding = false;
                    playerHeight = 40; // Stand up
                    playerWidth = 60;
                    playerY = defaultPlayerY;
                }
            }
        });

        // Game Loop
        timer = new Timer(20, this);
        timer.start();
    }

    private void restartGame() {
        score = 0;
        gameOver = false;
        currentSpeed = baseSpeed; // Reset speed
        obstacles.clear();
        particles.clear();
        playerY = defaultPlayerY;
        playerVelocityY = 0;
        isJumping = false;
        isSliding = false;
        playerHeight = 40;
        playerWidth = 60;
        flashAlpha = 255; // White flash on restart
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Draw Background (Sky)
        g.setColor(new Color(135, 206, 235)); // Sky Blue
        g.fillRect(0, 0, 800, 300);

        // Draw Sun
        g.setColor(Color.YELLOW);
        g.fillOval(680, 40, 60, 60);

        // Draw Scrolling Clouds
        g.setColor(Color.WHITE);
        for (int i = -1; i < 2; i++) {
            int cx = i * 800 - cloudOffset;
            g.fillOval(cx + 100, 60, 60, 40);
            g.fillOval(cx + 130, 50, 80, 50);
            g.fillOval(cx + 180, 60, 60, 40);
            g.fillOval(cx + 500, 80, 50, 30);
            g.fillOval(cx + 520, 70, 70, 40);
            g.fillOval(cx + 560, 80, 50, 30);
        }

        // Draw Scrolling Mountains
        g.setColor(new Color(100, 149, 237));
        for (int i = -1; i < 3; i++) {
            int mx = i * 400 - mountainOffset;
            g.fillPolygon(new int[]{mx, mx + 150, mx + 300}, new int[]{300, 150, 300}, 3);
            g.fillPolygon(new int[]{mx + 200, mx + 350, mx + 500}, new int[]{300, 100, 300}, 3);
        }

        // 2. Draw ground
        g.setColor(new Color(34, 139, 34)); // Forest Green
        g.fillRect(0, 300, 800, 100);
        
        // Scrolling Grass details
        g.setColor(new Color(0, 100, 0));
        for(int i = -40; i < 840; i += 40) {
            int gx = i - groundOffset;
            g.fillRect(gx, 310, 10, 5);
            g.fillRect(gx + 20, 330, 15, 5);
            g.fillRect(gx + 10, 350, 8, 4);
        }

        // 3. Draw Player (Fat Calico Cat)
        // Body
        g.setColor(Color.WHITE);
        g.fillRoundRect(playerX, playerY, playerWidth, playerHeight, 25, 25);
        
        // Orange patch
        g.setColor(Color.ORANGE);
        g.fillOval(playerX + 15, playerY, 20, 20);
        
        // Black patch
        g.setColor(Color.BLACK);
        g.fillOval(playerX + 35, playerY + 5, 15, 15);

        // Ears
        g.setColor(Color.WHITE);
        g.fillPolygon(new int[]{playerX + playerWidth - 20, playerX + playerWidth - 15, playerX + playerWidth - 10}, 
                      new int[]{playerY + 5, playerY - 10, playerY + 5}, 3);

        g.setColor(Color.ORANGE);
        g.fillPolygon(new int[]{playerX + playerWidth - 10, playerX + playerWidth - 5, playerX + playerWidth}, 
                      new int[]{playerY + 5, playerY - 10, playerY + 5}, 3);

        // Eyes
        g.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        if (gameOver) {
            // Dead eyes
            g.drawLine(playerX + playerWidth - 16, playerY + 8, playerX + playerWidth - 8, playerY + 16);
            g.drawLine(playerX + playerWidth - 16, playerY + 16, playerX + playerWidth - 8, playerY + 8);
        } else if (isSliding) {
            // Squinting eyes
            g.drawLine(playerX + playerWidth - 14, playerY + 12, playerX + playerWidth - 8, playerY + 12);
        } else {
            // Normal eyes
            g.fillOval(playerX + playerWidth - 12, playerY + 12, 4, 4);
        }

        // Legs Animation
        g.setColor(Color.WHITE);
        boolean runningAnim = (tick % 10) < 5;
        if (isJumping || isSliding || gameOver) {
            // Static legs
            g.fillRoundRect(playerX + 5, playerY + playerHeight - 5, 8, 12, 3, 3);
            g.fillRoundRect(playerX + 20, playerY + playerHeight - 5, 8, 12, 3, 3);
            g.fillRoundRect(playerX + playerWidth - 25, playerY + playerHeight - 5, 8, 12, 3, 3);
            g.fillRoundRect(playerX + playerWidth - 10, playerY + playerHeight - 5, 8, 12, 3, 3);
        } else if (runningAnim) {
            // Legs inward
            g.fillRoundRect(playerX + 15, playerY + playerHeight - 5, 8, 12, 3, 3);
            g.fillRoundRect(playerX + playerWidth - 20, playerY + playerHeight - 5, 8, 12, 3, 3);
        } else {
            // Legs outward
            g.fillRoundRect(playerX + 5, playerY + playerHeight - 5, 8, 12, 3, 3);
            g.fillRoundRect(playerX + 25, playerY + playerHeight - 5, 8, 12, 3, 3);
            g.fillRoundRect(playerX + playerWidth - 30, playerY + playerHeight - 5, 8, 12, 3, 3);
            g.fillRoundRect(playerX + playerWidth - 10, playerY + playerHeight - 5, 8, 12, 3, 3);
        }

        // Tail
        g.setColor(Color.BLACK);
        if (gameOver) {
            g.fillArc(playerX - 25, playerY + playerHeight - 10, 30, 10, 0, 180); // Droopy tail
        } else {
            g.fillArc(playerX - 15, playerY + 10, 30, 20, 90, 180); // Normal tail
        }

        // 4. Draw Obstacles (Black Dog)
        for (Obstacle obs : obstacles) {
            g.setColor(Color.BLACK);
            if (obs.isHigh) {
                // High obstacle (Flying dog)
                g.fillRoundRect(obs.x, obs.y, obs.width, obs.height, 10, 10);
                g.fillOval(obs.x - 15, obs.y + 5, 25, 25); // Head
                g.fillPolygon(new int[]{obs.x - 5, obs.x - 10, obs.x}, new int[]{obs.y + 5, obs.y - 10, obs.y + 10}, 3); // Ear
                g.fillRect(obs.x + 5, obs.y + obs.height, 5, 10); // Leg 1
                g.fillRect(obs.x + 25, obs.y + obs.height, 5, 10); // Leg 2
                g.fillRect(obs.x + obs.width, obs.y + 10, 15, 5); // Tail
                
                // Animated Wings
                g.setColor(Color.DARK_GRAY);
                if ((tick % 8) < 4) {
                    // Wing Up
                    g.fillPolygon(new int[]{obs.x + 15, obs.x + 30, obs.x + 40}, new int[]{obs.y + 15, obs.y - 15, obs.y + 5}, 3);
                } else {
                    // Wing Down
                    g.fillPolygon(new int[]{obs.x + 15, obs.x + 30, obs.x + 40}, new int[]{obs.y + 15, obs.y + 40, obs.y + 25}, 3);
                }
                g.setColor(Color.BLACK); // Reset to black
            } else {
                // Low obstacle (Running dog - bouncing)
                g.fillRoundRect(obs.x, obs.y, obs.width, obs.height - 10, 10, 10);
                g.fillOval(obs.x - 10, obs.y - 10, 25, 25); // Head
                g.fillPolygon(new int[]{obs.x, obs.x - 5, obs.x + 5}, new int[]{obs.y - 5, obs.y - 20, obs.y}, 3);
                g.fillRect(obs.x + obs.width, obs.y + 5, 15, 5);
                
                // Dog Leg Animation
                boolean dogRunAnim = (tick % 8) < 4;
                if(dogRunAnim || obs.y < obs.startY - 5) {
                    g.fillRect(obs.x + 5, obs.y + obs.height - 10, 5, 10);
                    g.fillRect(obs.x + 25, obs.y + obs.height - 10, 5, 10);
                } else {
                    g.fillRect(obs.x + 0, obs.y + obs.height - 10, 5, 10);
                    g.fillRect(obs.x + 30, obs.y + obs.height - 10, 5, 10);
                }
            }
        }

        // Draw Particles
        for (Particle p : particles) {
            int alpha = Math.max(0, Math.min(255, p.life * 15));
            g.setColor(new Color(p.color.getRed(), p.color.getGreen(), p.color.getBlue(), alpha));
            g.fillOval(p.x, p.y, p.size, p.size);
        }

        // Draw Flash Effect (Death or Restart)
        if (flashAlpha > 0) {
            if (gameOver) {
                g.setColor(new Color(255, 0, 0, flashAlpha)); // Red flash
            } else {
                g.setColor(new Color(255, 255, 255, flashAlpha)); // White flash
            }
            g.fillRect(0, 0, 800, 400);
        }

        // 5. Draw score, Speed Level & Game Over Text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + (score / 10), 10, 30);
        
        int level = currentSpeed - baseSpeed + 1;
        g.drawString("Level: " + level, 700, 30); // Display Speed Level

        if (gameOver) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 52));
            g.drawString("GAME OVER", 238, 152); // Drop shadow
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 240, 150);
            
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Score: " + (score / 10), 340, 200);
            g.drawString("Press Space or Up to Restart", 240, 250);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tick++;

        // Update Flash Effect
        if (flashAlpha > 0) {
            flashAlpha -= 10;
            if (flashAlpha < 0) flashAlpha = 0;
        }

        // Update Particles
        Iterator<Particle> pIter = particles.iterator();
        while (pIter.hasNext()) {
            Particle p = pIter.next();
            p.x += p.dx;
            p.y += p.dy;
            p.life--;
            if (p.life <= 0) pIter.remove();
        }

        if (gameOver) {
            repaint();
            return;
        }

        // Increase Difficulty (Speed and Spawn Rate)
        currentSpeed = baseSpeed + (score / 200); // Speed increases every 200 ticks
        if (currentSpeed > 25) currentSpeed = 25; // Max speed cap
        
        int spawnChance = 4 + (score / 400); // 4% base, increases over time
        if (spawnChance > 8) spawnChance = 8; // Max spawn cap

        // Update Scrolling Background based on currentSpeed
        groundOffset = (groundOffset + currentSpeed) % 40;
        cloudOffset = (cloudOffset + 1 + (currentSpeed - baseSpeed) / 2) % 800;
        mountainOffset = (mountainOffset + 3 + (currentSpeed - baseSpeed) / 2) % 400;

        // Player jump logic
        if (isJumping) {
            playerVelocityY += gravity;
            playerY += playerVelocityY;

            if (playerY >= defaultPlayerY) {
                playerY = defaultPlayerY;
                isJumping = false;
                playerVelocityY = 0;
                
                // Landing Dust Effect
                for(int i=0; i<4; i++) {
                    particles.add(new Particle(playerX + random.nextInt(40), playerY + playerHeight, 
                        random.nextInt(8)+4, random.nextInt(6)-3, -random.nextInt(2)-1, 10, Color.LIGHT_GRAY));
                }
            }
        }

        // Spawn obstacles dynamically based on new spawnChance
        if (random.nextInt(100) < spawnChance) { 
            if (obstacles.isEmpty() || obstacles.get(obstacles.size() - 1).x < (800 - currentSpeed * 15)) {
                boolean isHigh = random.nextBoolean();
                if (isHigh) {
                    // High obstacle (Flying dog)
                    obstacles.add(new Obstacle(800, 210, 40, 40, true));
                } else {
                    // Low obstacle (Bouncing dog)
                    obstacles.add(new Obstacle(800, 260, 40, 40, false));
                }
            }
        }

        // Update obstacles and check collision
        Iterator<Obstacle> iter = obstacles.iterator();
        int hitboxDuckOffset = isSliding ? 0 : 5;
        Rectangle playerRect = new Rectangle(playerX + 5, playerY + hitboxDuckOffset, playerWidth - 10, playerHeight - hitboxDuckOffset);

        while (iter.hasNext()) {
            Obstacle obs = iter.next();
            obs.x -= currentSpeed; // Game speed

            // Animate Bounce/Hover
            obs.angle += obs.bounceSpeed;
            if (obs.isHigh) {
                // Flying dog smoothly hovers up and down
                obs.y = (int) (obs.startY + Math.sin(obs.angle) * obs.bounceHeight);
            } else {
                // Ground dog hops up and down (only goes UP from startY)
                obs.y = (int) (obs.startY - Math.abs(Math.sin(obs.angle)) * obs.bounceHeight);
            }

            if (obs.x + obs.width < -20) {
                iter.remove();
            }

            Rectangle obsRect = new Rectangle(obs.x, obs.y, obs.width, obs.height);
            if (playerRect.intersects(obsRect)) {
                gameOver = true;
                flashAlpha = 150; // Death Flash
                
                // Death Explosion Effect
                for(int i=0; i<15; i++) {
                    particles.add(new Particle(playerX + 30, playerY + 20, 
                        random.nextInt(10)+5, random.nextInt(14)-7, random.nextInt(14)-7, 20, Color.RED));
                    particles.add(new Particle(playerX + 30, playerY + 20, 
                        random.nextInt(15)+5, random.nextInt(10)-5, random.nextInt(10)-5, 15, Color.ORANGE));
                }
            }
        }

        score++;
        repaint();
    }

    // Helper classes
    class Obstacle {
        int x, y, width, height;
        boolean isHigh;
        int startY;
        float angle = 0;
        float bounceSpeed;
        float bounceHeight;

        Obstacle(int x, int y, int width, int height, boolean isHigh) {
            this.x = x; this.y = y; this.width = width; this.height = height; this.isHigh = isHigh;
            this.startY = y;
            if (isHigh) {
                this.bounceHeight = 15; // Hover height
                this.bounceSpeed = 0.15f; // Hover speed
            } else {
                this.bounceHeight = 35; // Jump height
                this.bounceSpeed = 0.2f; // Jump speed
            }
        }
    }

    class Particle {
        int x, y, size, dx, dy, life;
        Color color;
        Particle(int x, int y, int size, int dx, int dy, int life, Color color) {
            this.x = x; this.y = y; this.size = size; this.dx = dx; this.dy = dy; this.life = life; this.color = color;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cat vs Dog Run (Speed Up + Bouncing)");
        DisplayGame game = new DisplayGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
