package theAlchemist.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAlchemist.AlchemistMod;
import theAlchemist.characters.TheAlchemist;
import theAlchemist.powers.OverhealPower;

import static theAlchemist.AlchemistMod.makeCardPath;

public class Overheal extends CustomCard
{// Template for a basic strike card
	public static final String ID = AlchemistMod.makeID(Overheal.class.getSimpleName());
	public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

	public static final String IMAGE = makeCardPath("Overheal.png");
	public static final String NAME = CARD_STRINGS.NAME;
	public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.DESCRIPTION;

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.POWER;
	private static final CardColor COLOR = TheAlchemist.Enums.COLOR_PLATINUM;

	private static final int COST = 2;

	public Overheal()
	{
		super(ID, NAME, IMAGE, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster)
	{
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new OverhealPower(player)));
	}
	
	@Override
	public void upgrade()
	{
		if(!upgraded)
		{
			upgradeName();
			this.isInnate = true;
			this.rawDescription = UPGRADE_DESCRIPTION + DESCRIPTION;
			initializeDescription();
		}
	}
}
