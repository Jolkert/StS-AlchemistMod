package theAlchemist.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theAlchemist.AlchemistMod;
import theAlchemist.cards.AbstractElement;
import theAlchemist.enums.AlchemistCardTags;
import theAlchemist.util.TextureLoader;

import static theAlchemist.AlchemistMod.makePowerPath;

public class ElementPower extends AbstractPower
{
	public static final String POWER_ID = AlchemistMod.makeID("ElementPower");
	private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = POWER_STRINGS.NAME;
	public static final String[] DESCRIPTIONS = POWER_STRINGS.DESCRIPTIONS;
	
	//IMAGES
	private static final Texture TEXTURE_84 = TextureLoader.getTexture(makePowerPath("84/element_power.png"));
	private static final Texture TEXTURE_32 = TextureLoader.getTexture(makePowerPath("32/element_power.png"));
	
	private AbstractElement element;
	
	public ElementPower(AbstractCreature owner, AbstractElement element)
	{
		this.name = NAME;
		this.ID = POWER_ID;
		
		this.owner = owner;
		
		this.isPostActionPower = true; // Again I don't know what this does but I think it applies - Jolkert 2020-02-06
		
		this.region128 = new TextureAtlas.AtlasRegion(TEXTURE_84, 0, 0, 84, 84);
		this.region48 = new TextureAtlas.AtlasRegion(TEXTURE_32, 0, 0, 32, 32);
		
		this.updateDescription();
		
		this.element = element;
	}
	
	@Override
	public void onPlayCard(AbstractCard playedCard, AbstractMonster monster)
	{
		if(playedCard == this.element)
			return;
		
		if(playedCard.hasTag(AlchemistCardTags.ELEMENT))
		{
			AbstractElement playedElement = (AbstractElement)playedCard;
			if(playedElement.products == null)
				return;
			
			if(!this.element.elementType.equals(playedElement.elementType))
			{
				AbstractElement newElement = combineElements(this.element, playedElement);
				
				if(newElement != null)
					AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(newElement, 1, true, true));
				
				AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
			}
		}
	}
	
	
	@Override
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0];// + this.element.type;
	}
	
	private AbstractElement combineElements(AbstractElement reactant1, AbstractElement reactant2)
	{
		int reactant2IndexInReactant1 = -1;
		for(int i = 0; i < reactant1.reactants.length; i++)
			if(reactant2.elementType.equals(reactant1.reactants[i].elementType))
			{
				reactant2IndexInReactant1 = i;
				break;
			}
		
		if(reactant2IndexInReactant1 == -1)
			return null;
		else
			return (AbstractElement)reactant1.products[reactant2IndexInReactant1].makeCopy();
	}
}
