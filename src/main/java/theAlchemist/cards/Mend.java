package theAlchemist.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAlchemist.AlchemistMod;
import theAlchemist.characters.TheAlchemist;

import static theAlchemist.AlchemistMod.makeCardPath;

public class Mend extends CustomCard
{// Template for a basic defend card
	public static final String ID = AlchemistMod.makeID(Mend.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

	public static final String IMG = makeCardPath("Mend.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final CardRarity RARITY = CardRarity.BASIC;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheAlchemist.Enums.COLOR_PLATINUM;

	private static final int COST = 1;
	private static final int HEAL = 5;
	private static final int UPGRADE_PLUS_HEAL = 3;


	public Mend()
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		
<<<<<<< HEAD
		baseHeal = HEAL;
		this.tags.add(BaseModCardTags.BASIC_DEFEND);
		this.tags.add(CardTags.HEALING);
=======
		baseMagicNumber = HEAL;
		this.tags.add(BaseModCardTags.BASIC_DEFEND); // Tag your strike, defend and form (Wraith form, Demon form, Echo form, etc.) cards so that they function correctly.
		this.tags.add(CardTags.HEALING); // Check these tags cause there might be something you need
>>>>>>> 712ac67285120be40890f24b8388204d913c997d
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster)
	{
		AbstractDungeon.actionManager.addToBottom(new HealAction(player, player, HEAL));
	}
	
	@Override
	public void upgrade()
	{
		if (!upgraded)
		{
			upgradeName();
			upgradeBlock(UPGRADE_PLUS_HEAL);
			initializeDescription();
		}
	}
}
