package theAlchemist;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.abstracts.CustomCard;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theAlchemist.characters.TheAlchemist;
import theAlchemist.relics.CursedAlembic;
import theAlchemist.util.IDCheckDontTouchPls;
import theAlchemist.util.TextureLoader;

import theAlchemist.cards.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;


@SpireInitializer
public class AlchemistMod implements EditCardsSubscriber,
									 EditRelicsSubscriber,
									 EditStringsSubscriber,
									 EditKeywordsSubscriber,
									 EditCharactersSubscriber,
									 PostInitializeSubscriber
{
	public static final Logger logger = LogManager.getLogger(AlchemistMod.class.getName());
	private static String modID;
	
	// Mod-settings settings. This is if you want an on/off savable button
	public static Properties theAlchemistDefaultSettings = new Properties();
	public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
	public static boolean enablePlaceholder = true;
	
	//This is for the in-game mod settings panel.
	private static final String MOD_NAME = "Alchemist Mod";
	private static final String AUTHOR = "Jolkert, Aron, Rad_Catholic_Dad, []";
	private static final String DESCRIPTION = "Adds a new alchemy-based character to the game";
	
	// ===============<TEXTURES>=================
 
	// Character Color
	public static final Color ALCHEMIST_PLATINUM = CardHelper.getColor(208, 243, 255);
	
	// Card backgrounds
	private static final String ATTACK_ALCHEMIST_PLATINUM = "theAlchemistResources/images/512/bg_attack_alchemist_platinum.png";
	private static final String SKILL_ALCHEMIST_PLATINUM = "theAlchemistResources/images/512/bg_skill_alchemist_platinum.png";
	private static final String POWER_ALCHEMIST_PLATINUM = "theAlchemistResources/images/512/bg_power_alchemist_platinum.png";
	
	private static final String ENERGY_ORB_ALCHEMIST_PLATINUM = "theAlchemistResources/images/512/card_alchemist_platinum_orb.png";
	private static final String CARD_ENERGY_ORB = "theAlchemistResources/images/512/card_small_orb.png";
	
	private static final String ATTACK_ALCHEMIST_PLATINUM_PORTRAIT = "theAlchemistResources/images/1024/bg_attack_alchemist_platinum.png";
	private static final String SKILL_ALCHEMIST_PLATINUM_PORTRAIT = "theAlchemistResources/images/1024/bg_skill_alchemist_platinum.png";
	private static final String POWER_ALCHEMIST_PLATINUM_PORTRAIT = "theAlchemistResources/images/1024/bg_power_alchemist_platinum.png";
	private static final String ENERGY_ORB_ALCHEMIST_PLATINUM_PORTRAIT = "theAlchemistResources/images/1024/card_alchemist_platinum_orb.png";
	
	// Character assets
	private static final String THE_ALCHEMIST_BUTTON = "theAlchemistResources/images/ui/charSelect/AlchemistCharacterButton.png";
	public static final String THE_ALCHEMIST_PORTRAIT = "theAlchemistResources/images/ui/charSelect/AlchemistCharacterPortraitBG.png";
	public static final String THE_ALCHEMIST_SHOULDER_1 = "theAlchemistResources/images/char/alchemistCharacter/shoulder.png";
	public static final String THE_ALCHEMIST_SHOULDER_2 = "theAlchemistResources/images/char/alchemistCharacter/shoulder2.png";
	public static final String THE_ALCHEMIST_CORPSE = "theAlchemistResources/images/char/alchemistCharacter/corpse.png";
	
    // Atlas and JSON files for the animations
    public static final String THE_ALCHEMIST_SKELETON_ATLAS = "theAlchemistResources/images/char/alchemistCharacter/skeleton.atlas";
    public static final String THE_ALCHEMIST_SKELETON_JSON = "theAlchemistResources/images/char/alchemistCharacter/skeleton.json";
    
	//Mod Badge
	public static final String BADGE_IMAGE = "theAlchemistResources/images/Badge.png";
	
	
	// ===============</TEXTURES>===============
	
	// ===============<MAKE PATHS>===============
	
	public static String makeCardPath(String resourcePath)
	{
		return getModID() + "Resources/images/cards/" + resourcePath;
	}
	
	public static String makeRelicPath(String resourcePath)
	{
		return getModID() + "Resources/images/relics/" + resourcePath;
	}
	
	public static String makeRelicOutlinePath(String resourcePath)
	{
		return getModID() + "Resources/images/relics/outline/" + resourcePath;
	}
	
	public static String makeOrbPath(String resourcePath)
	{
		return getModID() + "Resources/orbs/" + resourcePath;
	}
	
	public static String makePowerPath(String resourcePath)
	{
		return getModID() + "Resources/images/powers/" + resourcePath;
	}
	
	public static String makeEventPath(String resourcePath)
	{
		return getModID() + "Resources/images/events/" + resourcePath;
	}
	
	public static String makeUiPath(String resourcePath)
	{
		return getModID() + "Resources/images/ui/" + resourcePath;
	}
	
	// ===============</MAKE PATHS>===============
	
	public AlchemistMod()
	{
		logger.info("Subscribe to BaseMod hooks");
		BaseMod.subscribe(this);
		setModID("theAlchemist");
		logger.info("Done subscribing");
		
		logger.info("Creating color: " + TheAlchemist.Enums.COLOR_PLATINUM.toString());
		
		BaseMod.addColor(TheAlchemist.Enums.COLOR_PLATINUM, ALCHEMIST_PLATINUM, ALCHEMIST_PLATINUM, ALCHEMIST_PLATINUM,
				ALCHEMIST_PLATINUM, ALCHEMIST_PLATINUM, ALCHEMIST_PLATINUM, ALCHEMIST_PLATINUM,
				ATTACK_ALCHEMIST_PLATINUM, SKILL_ALCHEMIST_PLATINUM, POWER_ALCHEMIST_PLATINUM, ENERGY_ORB_ALCHEMIST_PLATINUM,
				ATTACK_ALCHEMIST_PLATINUM_PORTRAIT, SKILL_ALCHEMIST_PLATINUM_PORTRAIT, POWER_ALCHEMIST_PLATINUM_PORTRAIT,
				ENERGY_ORB_ALCHEMIST_PLATINUM_PORTRAIT, CARD_ENERGY_ORB);
		
		logger.info("Finished creating color");
		
		
		logger.info("Adding mod settings");
		// This loads the mod settings.
		// The actual mod Button is added below in receivePostInitialize()
		theAlchemistDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
		try
		{
			SpireConfig config = new SpireConfig("alchemistMod", "theAlchemistConfig", theAlchemistDefaultSettings); // ...right here
			// the "fileName" parameter is the name of the file MTS will create where it will save our setting.
			config.load(); // Load the setting and set the boolean to equal it
			enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		logger.info("Done adding mod settings");
		
	}
	

	
	
	@SuppressWarnings("unused")
	public static void initialize()
	{
		logger.info("========================= Initializing =========================");
		AlchemistMod alchemistMod = new AlchemistMod();
		logger.info("========================= Successfully Initialized =========================");
	}
	
	@Override
	public void receiveEditCharacters()
	{
		logger.info("Start editing characters. " + "Adding " + TheAlchemist.Enums.THE_ALCHEMIST.toString());
		
		BaseMod.addCharacter(new TheAlchemist("the Alchemist", TheAlchemist.Enums.THE_ALCHEMIST),
				THE_ALCHEMIST_BUTTON, THE_ALCHEMIST_PORTRAIT, TheAlchemist.Enums.THE_ALCHEMIST);
		receiveEditPotions();
		
		logger.info("Added " + TheAlchemist.Enums.THE_ALCHEMIST.toString());
	}
	
	@Override
	public void receivePostInitialize()
	{
		logger.info("Loading badge image");
		
		Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
		ModPanel settingsPanel = new ModPanel();
		ModLabel label = new ModLabel("No settings idiot.", 400.0f, 700.0f, settingsPanel, (me) -> {});
		BaseMod.registerModBadge(badgeTexture, MOD_NAME, AUTHOR, DESCRIPTION, settingsPanel);
		
		logger.info("Loaded badge image");
	}
	
	public void receiveEditPotions()
	{
	}
	
	@Override
	public void receiveEditRelics()
	{
		logger.info("Adding relics");
		
		BaseMod.addRelicToCustomPool(new CursedAlembic(), TheAlchemist.Enums.COLOR_PLATINUM);
		
		UnlockTracker.markRelicAsSeen(CursedAlembic.ID);
		
		logger.info("Relics added");
	}
	
	@Override
	public void receiveEditCards()
	{
		ArrayList<AbstractCard> cardsToAdd = generateCardsToAdd();
		for(AbstractCard card : cardsToAdd)
		{
			BaseMod.addCard(card);
			UnlockTracker.unlockCard(card.cardID);
		}
	}
	
	@Override
	public void receiveEditStrings()
	{
		logger.info("Beginning string editing for mod: " + getModID());
		
		BaseMod.loadCustomStringsFile(CharacterStrings.class, getModID() +  "Resources/localization/eng/AlchemistMod-Character-Strings.json");
		BaseMod.loadCustomStringsFile(CardStrings.class, getModID() + 		"Resources/localization/eng/AlchemistMod-Card-Strings.json");
		BaseMod.loadCustomStringsFile(UIStrings.class, getModID()+ 			"Resources/localization/eng/AlchemistMod-UI-Strings.json");
		BaseMod.loadCustomStringsFile(RelicStrings.class, getModID() +	    "Resources/localization/eng/AlchemistMod-Relic-Strings.json");
		BaseMod.loadCustomStringsFile(PowerStrings.class, getModID() + 		"Resources/localization/eng/AlchemistMod-Power-Strings.json");
		
		logger.info("Finished editing strings");
	}
	
	@Override
	public void receiveEditKeywords()
	{
		BaseMod.addKeyword(new String[]{"basic_element"},"Can be combined with other basic elements.");
		BaseMod.addKeyword(new String[]{"stage_1_element"}, "Can be combined with other Stage 1 Elements. Breaks down into its component basic elements on use.");
		BaseMod.addKeyword(new String[]{"stage_2_element"}, "Breaks down into its component basic elements on use.");
		BaseMod.addKeyword(new String[]{"Vitality"}, "Increases healing done by cards.");
	}
	
	
	private ArrayList<AbstractCard> generateCardsToAdd()
	{
		ArrayList<AbstractCard> retVal = new ArrayList<AbstractCard>();
		
		retVal.add(new Mend());
		retVal.add(new Strike_Platinum());
		retVal.add(new Brew());
		
		retVal.add(new Overheal());
		retVal.add(new LeadToGold());
		retVal.add(new Ignis());
		retVal.add(new Aer());
		retVal.add(new Terra());
		retVal.add(new Aqua());
		retVal.add(new Fulgur());
		retVal.add(new Glacies());
		retVal.add(new Naturae());
		retVal.add(new Tremor());
		retVal.add(new PotionJunky());
		retVal.add(new Violation());
		
		return retVal;
	}
    
    
	
	
	
	
    // IF YOU ARE COLLAPSED AND EVEN THOUGHT ABOUT TOUCHING THIS PART PLEASE EXPAND AND READ THE COMMENT BELOW AND READ IT. THERE'S A REASON THIS PART'S AT THE BOTTOM
    /*
     * Just don't touch this bit. It's for standardization between mods and helps things not be shit
     * Honestly I haven't even looked into what it does, but trust me it's important so just leave it be
     * You can look through it if you want but please just don't touch any of the code
     * You will break everything and I will be disappointed
     */
    public static void setModID(String ID)
    {
        Gson coolG = new Gson();
        InputStream in = AlchemistMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        logger.info("You are attempting to set your mod ID as: " + ID);
        if(ID.equals(EXCEPTION_STRINGS.DEFAULTID))
        {
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION);
        }
        else if(ID.equals(EXCEPTION_STRINGS.DEVID))
        {
            modID = EXCEPTION_STRINGS.DEFAULTID;
        }
        else
        {
            modID = ID;
        }
        logger.info("Success! ID is " + modID);
    }
    public static String getModID()
    {
        return modID;
    }
    private static void pathCheck()
	{
        Gson coolG = new Gson();
        InputStream in = AlchemistMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        String packageName = AlchemistMod.class.getPackage().getName();
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources");
        if(!modID.equals(EXCEPTION_STRINGS.DEVID))
        {
            if(!packageName.equals(getModID()))
            {
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID());
            }
            if(!resourcePathExists.exists())
            {
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources");
            }
        }
    }
    public static String makeID(String idText)
    {
        return getModID() + ":" + idText;
    }
}
