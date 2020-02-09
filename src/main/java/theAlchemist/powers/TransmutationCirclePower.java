package theAlchemist.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theAlchemist.AlchemistMod;
import theAlchemist.cards.AbstractElement;
import theAlchemist.enums.AlchemistCardTags;
import theAlchemist.util.TextureLoader;

import static theAlchemist.AlchemistMod.makePowerPath;

public class TransmutationCirclePower extends AbstractPower
{
	public AbstractCreature source;
	
	public static final String POWER_ID = AlchemistMod.makeID("TransmutationCirclePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	private boolean upgraded;
	
	//IMAGES
	private static final Texture TEXTURE_84 = TextureLoader.getTexture(makePowerPath("84/transmutation_circle_power.png"));
	private static final Texture TEXTURE_32 = TextureLoader.getTexture(makePowerPath("32/transmutation_circle_power.png"));
	
	public TransmutationCirclePower(AbstractCreature owner, boolean upgraded)
	{
		this.name = NAME;
		this.ID = POWER_ID;
		
		this.owner = owner;
		this.upgraded = upgraded;
		
		this.type = PowerType.BUFF;
		
		this.isPostActionPower = true; // I honestly have no clue what this flag does, but it sounds like it applies idk - Jolkert 2020-02-08
		
		this.region128 = new TextureAtlas.AtlasRegion(TEXTURE_84, 0, 0, 84, 84);
		this.region48 = new TextureAtlas.AtlasRegion(TEXTURE_32, 0, 0, 32, 32);
		
		this.updateDescription();
	}
	
	@Override
	public void onCardDraw(AbstractCard cardDrawn)
	{
		if(cardDrawn.hasTag(AlchemistCardTags.ELEMENT) && (this.upgraded || ((AbstractElement)cardDrawn).stage > 0))
			AbstractDungeon.actionManager.addToBottom(new UpgradeSpecificCardAction(cardDrawn));
	}
	
	@Override
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0];
	}
	
}
