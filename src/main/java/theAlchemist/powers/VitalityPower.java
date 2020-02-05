package theAlchemist.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import theAlchemist.AlchemistMod;
import theAlchemist.util.TextureLoader;

import static theAlchemist.AlchemistMod.makePowerPath;

public class VitalityPower extends AbstractPower
{
	public AbstractCreature source;
	
	public static final String POWER_ID = AlchemistMod.makeID("VitalityPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	public AbstractCard lastTrackedHeal;
	
	//IMAGES
	private static final Texture TEXTURE_84 = TextureLoader.getTexture(makePowerPath("84/vitality_power.png"));
	private static final Texture TEXTURE_32 = TextureLoader.getTexture(makePowerPath("32/vitality_power.png"));
	
	public VitalityPower(AbstractCreature owner, int amount)
	{
		this.name = NAME;
		this.ID = POWER_ID;
		
		this.owner = owner;
		this.amount = amount;
		checkCap();
		
		this.canGoNegative = true;
		
		this.region128 = new TextureAtlas.AtlasRegion(TEXTURE_84, 0, 0, 84, 84);
		this.region48 = new TextureAtlas.AtlasRegion(TEXTURE_32, 0, 0, 32, 32);
		this.updateDescription();
	}
	
	@Override
	public void stackPower(int stackAmount)
	{
		this.fontScale = 8.0f;
		this.amount += stackAmount;
		if(this.amount == 0)
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
		
		checkCap();
	}
	
	@Override
	public void reducePower(int reduceAmount)
	{
		this.fontScale = 8.0f;
		this.amount -= reduceAmount;
		if(this.amount == 0)
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
		
		checkCap();
	}
	
	@Override
	public void updateDescription()
	{
		if(this.amount > 0)
		{
			this.description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
			this.type = PowerType.BUFF;
		}
		else
		{
			this.description = DESCRIPTIONS[1] + DESCRIPTIONS[2] + (-this.amount) + DESCRIPTIONS[3];
			this.type = PowerType.DEBUFF;
		}
	}
	
	@Override
	public int onHeal(int healAmount)
	{
		return (healAmount += this.amount) < 0 ? 0 : healAmount;
		
	}
	
	private void checkCap()
	{
		if(this.amount > 999)
			this.amount = 999;
		else if(this.amount < -9999)
			this.amount = -999;
	}
}
