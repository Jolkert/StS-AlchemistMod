package theAlchemist.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theAlchemist.AlchemistMod;
import theAlchemist.characters.TheAlchemist;

import static theAlchemist.AlchemistMod.makeCardPath;

public class PotionJunky extends CustomCard
{// Template for a basic strike card
	public static final String ID = AlchemistMod.makeID(PotionJunky.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

	public static final String IMG = makeCardPath("PotionJunky.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	private static final CardColor COLOR = TheAlchemist.Enums.COLOR_PLATINUM;

	private static final int COST = 3;

	public PotionJunky()
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster)
	{
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, getBuffs(), damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
		if(upgraded)
		{
			for(AbstractPower power: AbstractDungeon.player.powers)
			{
				if(power.type == AbstractPower.PowerType.BUFF)
					power.stackPower(1);
			}
		}
	}
	
	@Override
	public void upgrade()
	{
		if(!upgraded)
		{
			upgradeName();
			rawDescription = DESCRIPTION + UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}

	private int getBuffs()
	{
		int buffs = 0;
		for(AbstractPower power: AbstractDungeon.player.powers)
		{
			if(power.type == AbstractPower.PowerType.BUFF)
				buffs += power.amount > 0 ? power.amount : 1;
		}
		return buffs;
	}
}
