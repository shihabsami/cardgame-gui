package view;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineSupport;
import view.interfaces.SelectionChangeFollower;
import view.model.PlayerState;

/**
 * JPanel where card graphics is displayed.
 */
public class CardPanel extends JPanel implements SelectionChangeFollower
{
    private Player selectedPlayer;
    private GameEngineSupport gameEngineSupport;

    /*
     * Brief explanation of the constants used in this class.
     *
     * "MAX_CARDS" - the maximum number of cards that can be drawn side-by-side
     * "TOTAL_SEPARATOR_COUNT" - number of separators/gaps between the cards
     * "CARD_HEIGHT_SCALE" - card width to height ratio as specified
     * "CARD_ROUNDING_RATIO" - the width to rounding size ratio of the rounding at the four corners
     * "SUIT_ICON_RATIO" - the scale of the suit icon relative to the card
     * "FONT_SCALE" - the scale of the font relative to the card
     * "CARD_TOP_TEXT_RATIO" - the proportion of top text's position relative to the card
     * "CARD_BOTTOM_TEXT_RATIO" - the proportion of bottom text's position relative to the card
     * "CARD_SHADOW_RATIO" - the width to shadow size ratio
     * "CARD_SHADOW_COLOR" - the color of the shadow
     * "SUIT_PATH" - the intermediate path to the suit images directory
     * "SUIT_IMAGES" - collection where suit images are cached during construction
     */
    private final int MAX_CARDS = 6;
    private final int TOTAL_SEPARATOR_COUNT = MAX_CARDS + 1;
    private final double CARD_HEIGHT_SCALE = 1.5f;
    private final double CARD_SEPARATOR_RATIO = 1/12f;
    private final double CARD_ROUNDING_RATIO = 1/5f;
    private final double SUIT_ICON_RATIO = 1/5f;
    private final double FONT_SCALE = 1/8f;
    private final float CARD_TOP_TEXT_RATIO = 1/10f;
    private final float CARD_BOTTOM_TEXT_RATIO = 9/10f;
    private final double CARD_SHADOW_RATIO = 1/30f;
    private final Color CARD_SHADOW_COLOR = new Color(230, 230, 230);
    private final String SUIT_PATH = String.format("img%s", File.separator);
    private final Map<PlayingCard.Suit, BufferedImage> SUIT_IMAGES = new HashMap<>();

    /**
     * Construct with the auxiliary game engine in order to query player states.
     * @param gameEngineSupport the GameEngineSupport that holds additional player related information
     */
    public CardPanel(GameEngineSupport gameEngineSupport)
    {
        setBorder(BorderFactory.createTitledBorder("Card Panel"));
              
        this.gameEngineSupport = gameEngineSupport;
        this.selectedPlayer = gameEngineSupport.getHousePlayer();

        // cache the suit images for (slightly) faster painting
        for (PlayingCard.Suit suit : PlayingCard.Suit.values())
        {
            try { SUIT_IMAGES.put(suit, readSuitImage(suit)); }
            catch (IOException exception) { exception.printStackTrace(); }
        }
    }

    @Override
    public void selectionChange(Player player)
    {
        selectedPlayer = player;
        repaint();
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        if (gameEngineSupport.getAllPlayers().size() != 0)
        {
	        // the cards for which graphics is to be drawn
	        PlayerState playerState = gameEngineSupport.getPlayerState(selectedPlayer);
	        List<PlayingCard> cards = playerState.getHand();
	
	        if (cards != null && cards.size() != 0)
	        {
	            // determine the width, height, separator and x, y coordinates based on the current dimension of the panel
	            int separatorWidth = (int) (getWidth() / MAX_CARDS * CARD_SEPARATOR_RATIO);
	            int cardWidth = ((getWidth() - TOTAL_SEPARATOR_COUNT * separatorWidth) / MAX_CARDS);
	            int cardHeight = ((int) (cardWidth * CARD_HEIGHT_SCALE));
	            int x = separatorWidth;
	            int y = half(getHeight()) - half(cardHeight);
	
	            // draw card graphics one by one
	            for (int i = 0; i < cards.size(); i++)
	            {
	                // get the three necessary attributes for drawing a card
	                BufferedImage suitImage = SUIT_IMAGES.get(cards.get(i).getSuit());
	                Color color = getSuitColor(cards.get(i).getSuit());
	                String valueString = getValueString(cards.get(i).getValue());
	
	                drawCardGraphics((Graphics2D) graphics, suitImage, valueString, color, x, y, cardWidth, cardHeight, 
	                		i == (cards.size() - 1) && playerState.hasBusted());
	                x += (cardWidth + separatorWidth);
	            }
	        }
        }
    }

    /**
     * Utility method to draw a single PLayingCard's graphics.
     * @param cardGraphics2D the Graphics2D object that is to be drawn on
     * @param suitImage the suit BufferedImage
     * @param valueString the value String that is places at the top-left and bottom-right
     * @param color the Color value of the card
     * @param x the x coordinate where drawing begins
     * @param y the y coordinate
     * @param width the width of the card being drawn
     * @param height the height of the card
     * @param busted true if the card caused a bust, false otherwise
     */
    private void drawCardGraphics(Graphics2D cardGraphics2D, BufferedImage suitImage, String valueString, Color color,
                                  int x, int y, int width, int height, boolean busted)
    {
        // determine the necessary attributes based on the provided width
        int roundingSize = (int) (width * CARD_ROUNDING_RATIO);
        double iconScale = width * SUIT_ICON_RATIO;
        double shadowScale = width * CARD_SHADOW_RATIO;

        // use anti-aliasing to soften the edges of shapes
        cardGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // draw the card's shadow
        cardGraphics2D.setColor(CARD_SHADOW_COLOR);
        cardGraphics2D.fillRoundRect((int) (x + shadowScale), (int) (y + shadowScale),
                width, height, roundingSize, roundingSize);

        // draw the card's body, colors chosen on whether the card caused a bust
        cardGraphics2D.setColor((busted) ? Color.LIGHT_GRAY : Color.WHITE);
        cardGraphics2D.fillRoundRect(x, y, width, height, roundingSize, roundingSize);

        // draw a properly scaled instance of the suit image at the centre
        Image scaledImage = suitImage.getScaledInstance((int) iconScale, (int) iconScale, Image.SCALE_SMOOTH);
        cardGraphics2D.drawImage(scaledImage, x + half(width) - half(scaledImage.getWidth(null)),
                y + half(height) - half(scaledImage.getHeight(null)), null);

        // determine the properties of the fonts to be used for the value String
        Font currentFont = cardGraphics2D.getFont();
        Font defaultFont = currentFont.deriveFont((float) (width * FONT_SCALE));
        cardGraphics2D.setFont(defaultFont);
        FontMetrics metrics = cardGraphics2D.getFontMetrics();

        // the padding is done since text at the top and the bottom are drawn at different positions
        int xPadding = metrics.stringWidth(valueString);
        int yPadding = half(metrics.getHeight()) - metrics.getDescent();

        // the coordinates (x1, y1) are for text at the top, (x2, y2) for the bottom
        float x1 = x + width * CARD_TOP_TEXT_RATIO;
        float y1 = y + height * CARD_TOP_TEXT_RATIO;
        float x2 = x + width * CARD_BOTTOM_TEXT_RATIO;
        float y2 = y + height * CARD_BOTTOM_TEXT_RATIO;
        
        cardGraphics2D.setColor(color);

        // y coordinate is padded to draw text from the top corner which by default draws from the bottom
        cardGraphics2D.drawString(valueString, x1, y1 + yPadding);

        // x coordinate is decreased to bring the text inside the card body, same logic for the y padding
        cardGraphics2D.drawString(valueString, x2 - xPadding, y2 + yPadding);
    }

    /**
     * Utility method to read the suit images from the file system.
     * @param suit the Suit enum for which the image is read
     * @return a BufferedImage based on the suit
     * @throws IOException thrown if reading the suit image fails
     */
    private BufferedImage readSuitImage(PlayingCard.Suit suit) throws IOException
    {
        return ImageIO.read(new File(String.format("%s%s.png", SUIT_PATH, suit.name())));
    }

    /**
     * Utility method to determine the color of the card.
     * @param suit the Suit enum of the PlayingCard
     * @return the color based on the suit
     */
    private Color getSuitColor(PlayingCard.Suit suit)
    {
        if (suit == PlayingCard.Suit.SPADES || suit == PlayingCard.Suit.CLUBS)
            return Color.BLACK;
        else
            return Color.RED;
    }

    /**
     * Utility method to determine the character to be drawn representing the card's value.
     * @param value the Value enum of the PlayingCard
     * @return single character String based on the value
     */
    private String getValueString(PlayingCard.Value value)
    {
        switch (value)
        {
            case EIGHT:
                return "8";
            case NINE:
                return "9";
            default:
                return value.name().substring(0, 1);
        }
    }

    /**
     * Utility method that does what it does best.
     * @param value the int value to be divided into half
     * @return the halved value
     */
    private int half(int value)
    {
        return value / 2;
    }
}
