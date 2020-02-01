package theAlchemist.ui.campfire;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import theAlchemist.AlchemistMod;
import theAlchemist.util.TextureLoader;
import theAlchemist.vfx.campfire.CampfireAlchemizeEffect;

import static theAlchemist.AlchemistMod.*;

public class AlchemizeOption extends AbstractCampfireOption
{
	private static final String ID = AlchemistMod.makeID("AlchemizeOption");
	private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
	private static final String[] TEXT = uiStrings.TEXT;
	private static final Texture IMAGE = TextureLoader.getTexture(makeUiPath("campfire/alchemize.png"));
	public static final int POTIONS_TO_RECEIVE = 2;
	
	public AlchemizeOption()
	{
		this.label = TEXT[0];
		this.description = TEXT[1] + POTIONS_TO_RECEIVE + TEXT[2];
		this.img = IMAGE;
	}
	
	@Override
	public void useOption()
	{
		AbstractDungeon.effectList.add(new CampfireAlchemizeEffect());
	}
}
