package theAlchemist.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import theAlchemist.AlchemistMod;
import theAlchemist.actions.ScientificBreakthroughAction;
import theAlchemist.characters.TheAlchemist;
import theAlchemist.powers.SpillagePower;

import static theAlchemist.AlchemistMod.makeCardPath;

public class ScientificBreakthrough extends CustomCard
{
	public static final String ID = AlchemistMod.makeID(ScientificBreakthrough.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	
	public static final String IMG = makeCardPath("ScientificBreakthrough.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	
	private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
	private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
	private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
	public static final AbstractCard.CardColor COLOR = TheAlchemist.Enums.COLOR_PLATINUM;
	
	private static final int COST = 1;
	private static final int CARD_DRAW = 3;
	private static final int UPGRADE_PLUS_DRAW = 1;
	
	
	public ScientificBreakthrough()
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = CARD_DRAW;
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster)
	{
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.magicNumber, new ScientificBreakthroughAction()));
	}
	
	@Override
	public void upgrade()
	{
		if (!upgraded)
		{
			upgradeName();
			upgradeMagicNumber(UPGRADE_PLUS_DRAW);
			initializeDescription();
		}
	}
}
