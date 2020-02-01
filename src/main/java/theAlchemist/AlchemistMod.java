package theAlchemist;

import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theAlchemist.characters.TheAlchemist;
import theAlchemist.util.IDCheckDontTouchPls;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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
	private static final String AUTHOR = "Jolkert, Aron, Rad_Catholic_Dad, ";
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
	private static final String THE_ALCHEMIST_BUTTON = "theAlchemistResources/images/charSelect/AlchemistCharacterButton.png";
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
	}
	
	public void receiveEditPotions()
	{
	}
	
	@Override
	public void receiveEditRelics()
	{
	}
	
	@Override
	public void receiveEditCards()
	{
	}
	
	@Override
	public void receiveEditStrings()
	{
		logger.info("Beginning string editing for mod: " + getModID());
		
		BaseMod.loadCustomStringsFile(CharacterStrings.class, getModID() + "Resources/localization/eng/AlchemistMod-Character-Strings.json");
		
		logger.info("Finished editing strings");
	}
	
	@Override
	public void receiveEditKeywords()
	{
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
