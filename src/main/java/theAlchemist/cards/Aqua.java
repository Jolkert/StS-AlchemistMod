package theAlchemist.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import theAlchemist.AlchemistMod;
import theAlchemist.characters.TheAlchemist;
import theAlchemist.enums.AlchemistCardTags;
import theAlchemist.powers.ElementPower;

import static theAlchemist.AlchemistMod.makeCardPath;

public class Aqua extends AbstractElement
{
	public static final String ID = AlchemistMod.makeID(Aqua.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	
	public static final String IMAGE = makeCardPath("Aqua.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	private static final CardColor COLOR = TheAlchemist.Enums.COLOR_PLATINUM;
	
	private static final int COST = 1;
	private static final int HEAL = 3;
	private static final int UPGRADE_PLUS_HEAL = 2; //upgrade
	private static final int REGEN = 2;
	
	public static final int STAGE = 0;
	public static final AbstractElement[] CONSTITUENTS = null;
	public static final AbstractElement[] REACTANTS = {new Aer(), new Terra()};
	public static final AbstractElement[] PRODUCTS  = {new Glacies(), new Naturae()};
	
	public Aqua()
	{
		super(ID, NAME, IMAGE, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "Water", STAGE, CONSTITUENTS, REACTANTS, PRODUCTS);
		this.baseMagicNumber = HEAL;
		this.exhaust = true;
		
		this.tags.add(CardTags.HEALING);
		this.tags.add(AlchemistCardTags.ELEMENT);
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster)
	{
		AbstractDungeon.actionManager.addToBottom(new HealAction(player, player, this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new RegenPower(player, REGEN)));
		super.use(player, monster); // Don't change this part if you're copy-pasting this for a new element pls thx -Jolkert 2020-02-06
	}
	
	@Override
	public void upgrade()
	{
		if(!upgraded)
		{
			upgradeName();
			upgradeMagicNumber(UPGRADE_PLUS_HEAL);
			initializeDescription();
		}
	}
}
