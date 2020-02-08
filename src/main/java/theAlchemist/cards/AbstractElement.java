package theAlchemist.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAlchemist.powers.ElementPower;

import java.util.ArrayList;

public abstract class AbstractElement extends CustomCard
{
	public String elementType; //  stores which element this element is, and is used for comparison purposes
	public int stage; // 0 == basic (default) (fire, water, earth, air)
					  // 1 == stage 1 combination (ice, lighting, quake, nature)
					  // 2 == stage 2 combination (life, death)
	public AbstractElement[] constituents; // defaults to null, array of elements that combine to make this element
	public AbstractElement[] reactants; // defaults to null, array of elements that can be combined with this element
	public AbstractElement[] products; // defaults to null, array of elements that can be produced from this element (must be in the same order as the reactants array)
	
	
	public AbstractElement(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target,
			String elementType, int stage, AbstractElement[] constituents, AbstractElement[] reactants, AbstractElement[] products)
	{
		super(id, name, img, cost, rawDescription, type, color, rarity, target);
		this.elementType = elementType;
		this.stage = stage;
		this.constituents = constituents;
		this.reactants = reactants;
		this.products = products;
	}
	
	public AbstractElement(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target,
			String elementType, int stage, AbstractElement[] constituents)
	{
		this(id, name, img, cost, rawDescription, type, color, rarity, target, elementType, stage, constituents, null, null);
	}
	
	public AbstractElement(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target,
			String elementType, int stage)
	{
		this(id, name, img, cost, rawDescription, type, color, rarity, target, elementType, stage, null, null, null);
	}
	public AbstractElement(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target,
			String elementType)
	{
		this(id, name, img, cost, rawDescription, type, color, rarity, target, elementType, 0, null, null, null);
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster)
	{
		if(!player.hasPower(ElementPower.POWER_ID) && this.products != null)
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ElementPower(player, this)));
		
		ArrayList<AbstractElement> basicsToAdd = new ArrayList<AbstractElement>();
		breakDown(this, basicsToAdd);
		
		for(AbstractElement element : basicsToAdd)
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(element.makeCopy(), 1));
	}
	
	private void breakDown(AbstractElement toBreakDown, ArrayList<AbstractElement> list)
	{
		if(toBreakDown.constituents != null)
		{
			for(AbstractElement element : toBreakDown.constituents)
			{
				if(element.constituents == null)
					list.add(element);
				else
					breakDown(toBreakDown, list);
			}
		}
	}
	
	public static AbstractCard returnRandomElement(int maxStage)
	{
		int randMax = (maxStage == 2) ? 10 : (maxStage * 2);
		int randInt = (int)(Math.random() * randMax);
		switch(randInt)
		{
			case 0: return new Aer();
			case 1: return new Aqua();
			case 2: return new Ignis();
			case 3: return new Terra();
			case 4: return new Fulgur();
			case 5: return new Glacies();
			case 6: return new Naturae();
			case 7: return new Tremor();
			default: return new Miracle();
		}
	}
}
