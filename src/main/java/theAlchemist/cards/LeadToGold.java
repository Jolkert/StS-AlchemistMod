package theAlchemist.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAlchemist.AlchemistMod;
import theAlchemist.characters.TheAlchemist;

import static theAlchemist.AlchemistMod.makeCardPath;

public class LeadToGold extends CustomCard
{// Template for a basic strike card
	public static final String ID = AlchemistMod.makeID(AttackTemplate.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

	public static final String IMG = makeCardPath("LeadToGold");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	private static final CardColor COLOR = TheAlchemist.Enums.COLOR_PLATINUM;

	private static final int COST = 1;
	private static final int GOLD_GAIN_MULTIPLIER = 5;
	private static final int UPGRADE_PLUS_GOLD_GAIN_MULTIPLIER = 2;

	public LeadToGold()
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		
		baseMagicNumber = GOLD_GAIN_MULTIPLIER;
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster)
	{
		int goldToGain = monster.currentBlock * this.magicNumber;
		AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(monster, player));
		if(goldToGain > 0)
			AbstractDungeon.actionManager.addToBottom(new GainGoldAction(goldToGain));
	}
	
	@Override
	public void upgrade()
	{
		if(!upgraded)
		{
			upgradeName();
			upgradeMagicNumber(UPGRADE_PLUS_GOLD_GAIN_MULTIPLIER);
			initializeDescription();
		}
	}
}
