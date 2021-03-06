package theAlchemist.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import theAlchemist.AlchemistMod;
import theAlchemist.characters.TheAlchemist;
import theAlchemist.powers.SpillagePower;

import static theAlchemist.AlchemistMod.makeCardPath;

public class Brew extends CustomCard
{// Template for a basic defend card
	public static final String ID = AlchemistMod.makeID(Brew.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

	public static final String IMG = makeCardPath("Brew.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	private static final CardRarity RARITY = CardRarity.BASIC;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheAlchemist.Enums.COLOR_PLATINUM;

	private static final int COST = 1;
	private static final int POTIONS = 1;
	private static final int UPGRADE_PLUS_POTIONS = 1;


	public Brew()
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = POTIONS;

	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster)
	{
		for(int i = 0; i < this.baseMagicNumber; i++)
		{
			AbstractPotion potionToAdd = AbstractDungeon.returnRandomPotion(true);
			AbstractDungeon.actionManager.addToBottom(new ObtainPotionAction(potionToAdd));
			
			if(player.hasPower(SpillagePower.POWER_ID))
				potionToAdd.makeCopy().use(monster); // This might break but hopefully not -Jolkert 2020-02-07
		}
	}
	
	@Override
	public void upgrade()
	{
		if (!upgraded)
		{
			upgradeName();
			upgradeMagicNumber(UPGRADE_PLUS_POTIONS);
			this.rawDescription = UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}
}
