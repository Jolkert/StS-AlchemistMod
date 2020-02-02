package theAlchemist.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theAlchemist.AlchemistMod;
import theAlchemist.relics.CursedAlembic;

import java.util.ArrayList;

import static theAlchemist.AlchemistMod.*;
import static theAlchemist.characters.TheAlchemist.Enums.COLOR_PLATINUM;

public class TheAlchemist extends CustomPlayer
{
	public static final Logger logger = LogManager.getLogger(AlchemistMod.class.getName());
	
	public static class Enums
	{
		@SpireEnum
		public static AbstractPlayer.PlayerClass THE_ALCHEMIST;
		
		@SpireEnum(name = "ALCHEMIST_PLATINUM_COLOR")
		public static AbstractCard.CardColor COLOR_PLATINUM;
		
		@SpireEnum(name = "ALCHEMIST_PLATINUM_COLOR")
		public static CardLibrary.LibraryType LIBRARY_COLOR;
	}
	
	// ==============<BASE STATS>==============
	
	public static final int ENERGY_PER_TURN = 3;
	public static final int STARTING_HP = 80;
	public static final int MAX_HP = STARTING_HP;
	public static final int STARTING_GOLD = 99;
	public static final int CARD_DRAW = 5;
	public static final int ORB_SLOTS = 0;
	
	// ==============</BASE STATS>==============
	
	
	// ==============<STRINGS>==============
	
	private static final String ID = makeID("AlchemistCharacter");
	private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
	private static final String[] NAMES = characterStrings.NAMES;
	private static final String[] TEXT = characterStrings.TEXT;
	
	// ==============</STRINGS>==============
	
	
	public static final String[] ORB_TEXTURES = {
			"theAlchemistResources/images/char/alchemistCharacter/orb/1.png",
			"theAlchemistResources/images/char/alchemistCharacter/orb/2.png",
			"theAlchemistResources/images/char/alchemistCharacter/orb/3.png",
			"theAlchemistResources/images/char/alchemistCharacter/orb/4.png",
			"theAlchemistResources/images/char/alchemistCharacter/orb/5.png",
			"theAlchemistResources/images/char/alchemistCharacter/orb/border.png",
			"theAlchemistResources/images/char/alchemistCharacter/orb/1d.png",
			"theAlchemistResources/images/char/alchemistCharacter/orb/2d.png",
			"theAlchemistResources/images/char/alchemistCharacter/orb/3d.png",
			"theAlchemistResources/images/char/alchemistCharacter/orb/4d.png",
			"theAlchemistResources/images/char/alchemistCharacter/orb/5d.png"
	};
	
	public static final float[] LAYER_SPEEDS = {
			0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
	};
	
	public TheAlchemist(String name, PlayerClass setClass)
	{// A lot of this will probably have to change when we add the actual character sprites. It's all placeholder -Jolkert 2020-01-30
		super(name, setClass, ORB_TEXTURES, "theAlchemistResources/images/char/alchemistCharacter/orb/vfx.png", LAYER_SPEEDS,
				new SpriterAnimation("theAlchemistResources/images/char/alchemistCharacter/Spriter/theAlchemistAnimation.scml"));
		
		initializeClass(null, THE_ALCHEMIST_SHOULDER_2, THE_ALCHEMIST_SHOULDER_1, THE_ALCHEMIST_CORPSE, getLoadout(), 20, -10, 220, 290,
				new EnergyManager(ENERGY_PER_TURN));
		
		loadAnimation(THE_ALCHEMIST_SKELETON_ATLAS, THE_ALCHEMIST_SKELETON_JSON, 1);
		AnimationState.TrackEntry e = state.setAnimation(0, "animation", true);
		e.setTime(e.getEndTime() * MathUtils.random());
		
		dialogX = (drawX + 0 * Settings.scale);
		dialogY = (drawY + 220 * Settings.scale);
	}
	
	@Override
	public String getPortraitImageName()
	{// More temp stuff. Once we actually have the portrait image, we'll put that path here. For now it's a temp -Jolkert 2020-30-01
		return THE_ALCHEMIST_PORTRAIT;
	}
	
	@Override
	public ArrayList<String> getStartingDeck() // DONE
	{// Until we implement our actual cards, he starts with an ironclad starter deck -Jolkert 2020-01-30
		ArrayList<String> retVal = new ArrayList<String>();
		
		retVal.add("Strike_R");
		retVal.add("Strike_R");
		retVal.add("Strike_R");
		retVal.add("Strike_R");
		retVal.add("Strike_R");
		retVal.add("Defend_R");
		retVal.add("Defend_R");
		retVal.add("Defend_R");
		retVal.add("Defend_R");
		retVal.add("Bash");
		
		return retVal;
	}
	
	@Override
	public ArrayList<String> getStartingRelics() // DONE
	{// Until we make the starter relic, we're just gonna start with the potion belt
		ArrayList<String> retVal = new ArrayList<String>();
		
		retVal.add(CursedAlembic.ID);
		
		for(String id : retVal)
			UnlockTracker.markRelicAsSeen(id);
		
		return retVal;
	}
	
	@Override
	public CharSelectInfo getLoadout() // DONE
	{
		return new CharSelectInfo(NAMES[0], TEXT[0], STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW,
				this, getStartingRelics(), getStartingDeck(), false);
	}
	
	@Override
	public String getTitle(PlayerClass playerClass) // DONE
	{// Class name as it appears next to the player name in-game
		return NAMES[1];
	}
	
	@Override
	public AbstractCard.CardColor getCardColor() // DONE
	{
		return COLOR_PLATINUM;
	}
	
	@Override
	public Color getCardRenderColor() // DONE
	{
		return ALCHEMIST_PLATINUM;
	}
	
	@Override
	public AbstractCard getStartCardForEvent() // DONE
	{// This card is what the matching game gives you every time. In basegame: red -> bash, green -> neutralize, blue -> zap, purple -> eruption. Gonna have to change this later -Jolkert 2020-01-30
		return new com.megacrit.cardcrawl.cards.red.ThunderClap();
	}
	
	@Override
	public Color getCardTrailColor() // DONE
	{
		return ALCHEMIST_PLATINUM;
	}
	
	@Override
	public int getAscensionMaxHPLoss() // DONE
	{// This is how I leaned this mechanic even exists. We'll leave it 0 for now -Jolkert 2020-01-30
		return 0;
	}
	
	@Override
	public BitmapFont getEnergyNumFont() // DONE
	{
		return FontHelper.energyNumFontRed;
	}
	
	@Override
	public void doCharSelectScreenSelectEffect() // DONE
	{// Until we get a better sound effect, we've got the magic beam -Jolkert 2020-01-30
		CardCrawlGame.sound.playA("ATTACK_MAGIC_BEAM_SHORT", 1.25f);
		CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);
	}
	
	@Override
	
	
	public String getCustomModeCharacterButtonSoundKey() // DONE
	{// Again, it's the beam sound temporarily
		return "ATTACK_MAGIC_BEAM_SHORT";
	}
	
	@Override
	public String getLocalizedCharacterName() // DONE
	{
		return NAMES[0];
	}
	
	@Override
	public AbstractPlayer newInstance() // DONE
	{
		return new TheAlchemist(name, chosenClass);
	}
	
	@Override
	public String getSpireHeartText() // DONE
	{
		return TEXT[1];
	}
	
	@Override
	public Color getSlashAttackColor() // DONE
	{// Text that displays when you are about to attack the heart
		return ALCHEMIST_PLATINUM;
	}
	
	@Override
	public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() // DONE
	{// Animations are played in order when attacking the heart at the end of a run. Can be any size > 0
		return new AbstractGameAction.AttackEffect[]{
				AbstractGameAction.AttackEffect.LIGHTNING,
				AbstractGameAction.AttackEffect.FIRE,
				AbstractGameAction.AttackEffect.BLUNT_HEAVY
		};
	}
	
	@Override
	public String getVampireText() // DONE
	{// This is basically so the vampires can call the character different things (i.e. brother/sister/broken one) depending on character. Idk what we want them to call us -Jolkert 2020-01-30
		return TEXT[2];
	}
}
