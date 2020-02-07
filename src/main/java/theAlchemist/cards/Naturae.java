package theAlchemist.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAlchemist.AlchemistMod;
import theAlchemist.enums.AlchemistCardTags;
import theAlchemist.powers.ElementPower;
import theAlchemist.powers.VitalityPower;

import static theAlchemist.AlchemistMod.makeCardPath;

public class Naturae extends AbstractElement
{
	public static final String ID = AlchemistMod.makeID(Naturae.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	
	public static final String IMAGE = makeCardPath("Naturae.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	
	private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL;
	private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
	private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
	private static final AbstractCard.CardColor COLOR = CardColor.COLORLESS;
	
	private static final int COST = 2;
	private static final int HEAL = 10;
	private static final int VITALITY = 3;
	private static final int UPGRADE_PLUS_HEAL = 2; //upgrade
	
	public static final int STAGE = 1;
	public static final AbstractElement[] CONSTITUENTS = {new Aqua(), new Terra()};
	public static final AbstractElement[] REACTANTS = null;
	public static final AbstractElement[] PRODUCTS  = null;
	
	public Naturae()
	{
		super(ID, NAME, IMAGE, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "Nature", STAGE, CONSTITUENTS, REACTANTS, PRODUCTS);
		this.baseMagicNumber = HEAL;
		this.exhaust = true;
		
		this.tags.add(CardTags.HEALING);
		this.tags.add(AlchemistCardTags.ELEMENT);
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster)
	{
		AbstractDungeon.actionManager.addToBottom(new HealAction(player, player, this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new VitalityPower(player, VITALITY), VITALITY));
		super.use(player, monster); // Don't change these parts if you're copy-pasting this for a new element pls thx -Jolkert 2020-02-06
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
