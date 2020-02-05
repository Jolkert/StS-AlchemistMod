package theAlchemist.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
	}
	
	@Override
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0];
	}
	
	@Override
	public int onHeal(int healAmount)
	{
		return healAmount + this.amount;
		
	}
}
