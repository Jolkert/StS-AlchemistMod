package theAlchemist.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

import java.util.ArrayList;

import static theAlchemist.AlchemistMod.*;

public class OverhealPower extends AbstractPower
{
	public AbstractCreature source;
	
	public static final String POWER_ID = AlchemistMod.makeID("OverhealPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	private AbstractCard lastTrackedHeal;
	
	//IMAGES
	private static final Texture TEXTURE_84 = TextureLoader.getTexture(makePowerPath("84/overheal_power.png"));
	private static final Texture TEXTURE_32 = TextureLoader.getTexture(makePowerPath("32/overheal_power.png"));
	
	public OverhealPower(AbstractCreature owner)
	{
		this.name = NAME;
		this.ID = POWER_ID;
		
		this.owner = owner;
		
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		
		this.region128 = new TextureAtlas.AtlasRegion(TEXTURE_84, 0, 0, 84, 84);
		this.region48 = new TextureAtlas.AtlasRegion(TEXTURE_32, 0, 0, 32, 32);
		
		updateDescription();
		
		lastTrackedHeal = getLastPlayedHealCard();
	}
	
	@Override
	public int onHeal(int healAmount)
	{
		int regenToAdd = owner.currentHealth + healAmount - owner.maxHealth;
		
		if(regenToAdd > 0 && lastHealWasCard())
		{
			flashWithoutSound();
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new RegenPower(owner, regenToAdd)));
			
			AbstractCard lastHeal = getLastPlayedHealCard();
			if(lastHeal != null)
				lastTrackedHeal = lastHeal;
		}
		
		return healAmount;
		
	}
	
	@Override
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0];
	}
	
	private AbstractCard getLastPlayedHealCard()
	{
		ArrayList<AbstractCard> cardsThisCombat = AbstractDungeon.actionManager.cardsPlayedThisCombat;
		
		AbstractCard lastPlayedHeal = null;
		for(int i = cardsThisCombat.size() - 1; i >= 0; i--)
			if(cardsThisCombat.get(i).hasTag(AbstractCard.CardTags.HEALING))
			{
				lastPlayedHeal = cardsThisCombat.get(i);
				break;
			}
		
		return lastPlayedHeal;
	}
	
	private boolean lastHealWasCard()
	{
		return lastTrackedHeal == getLastPlayedHealCard();
	}
}
